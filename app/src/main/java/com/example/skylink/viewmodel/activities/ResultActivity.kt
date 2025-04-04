package com.example.skylink.viewmodel.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.model.singletons.CompanionObjects.Companion.STATIONS_MAKER
import com.example.skylink.viewmodel.adapters.EstacionesAdapter
import com.example.skylink.model.customDataStructures.RespuestaOptimizador
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK
import com.example.skylink.model.singletons.CompanionObjects.Companion.LAST_ROUTE_SINGLETON
import com.example.skylink.viewmodel.adapters.DialogTerminalAdapter

//Activity que muestra toda la información de la ruta optimizada, como el tiempo requerido, el recorrido y el precio
class ResultActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivityResultBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }
    private var estacionOrigen = -1
    private var estacionDestino = -1
    private var tiempo = -1
    private lateinit var recorrido: IntArray
    private var precio = -1.0
    private var listaEstaciones = STATIONS_MAKER.loadStationList(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Determinamos el modo en que se llegó a esta Activity
        // puede ser una solicitud nueva o recarga de una anterior
        val llamada = intent.getStringExtra(ID_LLAMADA_SKYLINK) ?: "Error"
        //Cargamos los datos desde la respuesta del Optimizador o del Proxy
        cargaDeDatos(llamada)

        //Establecer el valor de tiempo
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
            recorrido.add(listaEstaciones.get(this.recorrido[estacion]))
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

    //Función que solicita la respuesta del Optimizador y procesa sus datos
    private fun cargaDeDatos(llamada: String) {
        var respuesta: RespuestaOptimizador
        when (llamada) {
            "Optimizar" -> {
                //Estaciones a usar en la optimización de la ruta
                estacionOrigen = intent.getIntExtra(ID_INPUT_BEGIN, -1)
                estacionDestino = intent.getIntExtra(ID_INPUT_END, -1)

                //Respuesta`desde SkyLink
                respuesta = LAST_ROUTE_SINGLETON.getInstance(this).optimizarRuta(estacionOrigen, estacionDestino)
            }
            "Recargar" -> {
                //Carga la respuesta desde el cache de Proxy
                respuesta = LAST_ROUTE_SINGLETON.getInstance(this).getLastOptimization()
            }
            //Si la llamada aún no está establecida, se lanza una excepción
            else -> throw IllegalArgumentException("Valor de llamada $llamada no válido")
        }
        tiempo = respuesta.tiempo
        recorrido = respuesta.recorrido
        precio = respuesta.precio
    }

    //TODO Al hacer click en las estaciones se debería mostrar una descripción de la misma
    override fun onItemClick(input: Int) {
        val terminal = listaEstaciones.get(input)
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_terminal)

        // Button back
        val buttonBack = dialog.findViewById<ImageButton>(R.id.dialog_terminal_button_back)
        buttonBack.setOnClickListener {
            dialog.dismiss()
        }

        // Título
        val textTitle = dialog.findViewById<TextView>(R.id.dialog_terminal_title)
        textTitle.text = terminal.nombre

        //Color
        val image = dialog.findViewById<ImageView>(R.id.dialog_terminal_image)
        image.setColorFilter(ContextCompat.getColor(this, COLOR_GETTER.getColorID(terminal.lineas[0])))
        dialog.show()

        //Recycler
        val recyclerDialogTerminalAdapter by lazy { DialogTerminalAdapter() }
        val recycler = dialog.findViewById<RecyclerView>(R.id.dialog_terminal_recycler)
        recyclerDialogTerminalAdapter.addDataToList(terminal.lineas)
        recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerDialogTerminalAdapter
        }

    }

    //Se sobreescribe el uso de onBackPressed para evitar que se pueda volver a la selección de estaciones
    // ya que puede generar errores si no se inicia nuevamente la Activity
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
        startActivity(intent)
        super.onBackPressed()
    }
}