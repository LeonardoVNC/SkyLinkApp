package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.model.singletons.CompanionObjects.Companion.LIST_ESTACIONES
import com.example.skylink.viewmodel.adapters.EstacionesAdapter
import com.example.skylink.model.customDataStructures.RespuestaOptimizador
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK
import com.example.skylink.model.singletons.CompanionObjects.Companion.LAST_ROUTE_SINGLETON

class ResultActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivityResultBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }
    private var nodoInicial = -1
    private var nodoFinal = -1
    private var tiempo = -1
    private lateinit var recorrido: IntArray
    private var precio = -1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val llamada = intent.getStringExtra(ID_LLAMADA_SKYLINK) ?: "Error"
        cargaDeDatos(llamada)

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
            binding.resultPrice.text = "${String.format("%.2f", precio)} ${getString(R.string.result_price)}"
        }

        //Mostrar la ruta recorrida
        val recorrido = mutableListOf<Estacion>()
        for (estacion in 0 until this.recorrido.size) {
            if(this.recorrido[estacion] == -1) {
                break
            }
            recorrido.add(LIST_ESTACIONES.get(this.recorrido[estacion]))
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

    private fun cargaDeDatos(llamada: String) {
        var respuesta: RespuestaOptimizador
        when (llamada) {
            "Optimizar" -> {
                //Nodos a usar en la optimización de la ruta
                nodoInicial = intent.getIntExtra(ID_INPUT_BEGIN, -1)
                nodoFinal = intent.getIntExtra(ID_INPUT_END, -1)

                //Respuesta`desde SkyLink
                respuesta = LAST_ROUTE_SINGLETON.getInstance(this).optimizarRuta(nodoInicial, nodoFinal)
            }
            "Recargar" -> {
                //Carga la respuesta desde el cache de Proxy
                respuesta = LAST_ROUTE_SINGLETON.getInstance(this).getLastOptmization()
            }
            else -> throw IllegalArgumentException("Valor de llamada $llamada no válido")
        }
        tiempo = respuesta.tiempo
        recorrido = respuesta.recorrido
        precio = respuesta.precio
    }

    override fun onItemClick(input: Int) {}

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
        startActivity(intent)
        super.onBackPressed()
    }
}