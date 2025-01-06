package com.example.skylink

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.SelectPricesActivity.Companion.ID_SELECTED_PRICE
import com.example.skylink.SelectTerminalActivity.Companion.ID_INPUT_BEGIN
import com.example.skylink.SelectTerminalActivity.Companion.ID_INPUT_END
import com.example.skylink.SelectTerminalActivity.Companion.LIST_ESTACIONES
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding



class ResultActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivityResultBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }
    private var nodoInicial = -1
    private var nodoFinal = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        nodoInicial = intent.getIntExtra(ID_INPUT_BEGIN, -1)
        nodoFinal = intent.getIntExtra(ID_INPUT_END, -1)

        val miSkyLink = SkyLink()
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        miSkyLink.inicializarGrafo()

        //Calcular el tiempo
        val respuestaTiempo = miSkyLink.optimizacionTiempo(nodoInicial,nodoFinal)
        val tiempo = respuestaTiempo[0]
        if (tiempo  == -1) {
            println("Ha ocurrido un error en la conexión de los nodos, verifique SkyLink.inicializarGrafo()")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        } else {
            binding.resultTime.text = "$tiempo ${getString(R.string.result_time)}"
        }

        //Calcular el precio
        miSkyLink.setTipoCliente(sharedPreferences.getInt(ID_SELECTED_PRICE, 1))
        val respuestaPrecio = miSkyLink.optimizacionPrecio(nodoInicial, nodoFinal)
        val precio = respuestaPrecio[0]
        if (precio == -1.0) {
            println("Ha ocurrido un error en la conexión de los nodos, verifique SkyLink.inicializarGrafo()")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }else {
            binding.resultPrice.text = "$precio ${getString(R.string.result_price)}"
        }

        //Comprobar misma ruta
        for (estacion in 1 until respuestaTiempo.size) {
            if (respuestaTiempo[estacion].toDouble() != respuestaPrecio[estacion]){
                println("Puede que exista inconcordia en los caminos, verifica que el precio y la ruta proporcionada concuerden")
            }
        }

        //Mostrar la ruta recorrida
        val recorrido = mutableListOf<Estacion>()
        for (estacion in 1 until respuestaTiempo.size) {
            if (respuestaTiempo[estacion] != -1) {
                recorrido.add(
                    LIST_ESTACIONES.get(respuestaTiempo[estacion])
                )
            } else {
                break
            }
        }
        setUpRecyclerView(recorrido)

        binding.resultButtonReset.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }
    }

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView(listaDeDatos: List<Estacion>) {
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.resultRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }

    override fun onItemClick(input: Int) {}
}