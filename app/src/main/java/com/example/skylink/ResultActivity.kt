package com.example.skylink

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.Singletons.Companion.APP_PREFERENCES
import com.example.skylink.Singletons.Companion.ID_INPUT_BEGIN
import com.example.skylink.Singletons.Companion.ID_INPUT_END
import com.example.skylink.Singletons.Companion.ID_SELECTED_PRICE
import com.example.skylink.Singletons.Companion.LIST_ESTACIONES
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
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        //Nodos a usar en la optimización de la ruta
        nodoInicial = intent.getIntExtra(ID_INPUT_BEGIN, -1)
        nodoFinal = intent.getIntExtra(ID_INPUT_END, -1)

        //Instanciación de SkyLink.java
        val precioSeleccionado = sharedPreferences.getString(ID_SELECTED_PRICE, "Estándar")
        val miSkyLink = SkyLink(precioSeleccionado)

        //Respuesta`desde SkyLink
        val respuesta = miSkyLink.optimizarRuta(nodoInicial, nodoFinal);
        val tiempo = respuesta.intArr[0]
        val intArr = respuesta.intArr
        val precio = respuesta.doubleValue

        //Establecer el tiempo
        if (tiempo  == -1) {
            println("Ha ocurrido un error en la conexión de los nodos, verifique SkyLink.inicializarGrafo()")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        } else {
            binding.resultTime.text = "$tiempo ${getString(R.string.result_time)}"
        }

        //Establecer el precio
        if (precio == -1.0) {
            println("Ha ocurrido un error en la conexión de los nodos, verifique SkyLink.inicializarGrafo()")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }else {
            binding.resultPrice.text = "$precio ${getString(R.string.result_price)}"
        }

        //Mostrar la ruta recorrida
        val recorrido = mutableListOf<Estacion>()
        for (estacion in 1 until intArr.size) {
            if(intArr[estacion] == -1) {
                break
            }
            recorrido.add(LIST_ESTACIONES.get(intArr[estacion]))
        }
        setUpRecyclerView(recorrido)

        //Button Listeners
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
        startActivity(intent)
        super.onBackPressed()
    }
}