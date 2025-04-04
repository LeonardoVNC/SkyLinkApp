package com.example.skylink.viewmodel.activities

import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.model.singletons.CompanionObjects.Companion.STATIONS_MAKER
import com.example.skylink.viewmodel.adapters.EstacionesAdapter
import com.example.skylink.model.customDataStructures.RespuestaOptimizador
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding
import com.example.skylink.databinding.DialogTerminalBinding
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
    private var listaEstaciones = STATIONS_MAKER.loadTerminalList(this)

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
            eraseBackStack()
        } else {
            binding.resultTime.text = "$tiempo ${getString(R.string.result_time)}"
        }

        //Establecer el precio
        if (precio == -1.0) {
            println("Ha ocurrido un error en la conexión de los nodos, verifique SkyLink.inicializarGrafo()")
            eraseBackStack()
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
        setUpRecyclerView(recorrido.reversed())

        //Button Listeners
        binding.resultButtonReset.setOnClickListener {
            eraseBackStack()
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

    override fun onItemClick(input: Int) {
        val terminal = listaEstaciones[input]
        val dialog = Dialog(this, R.style.DialogSinFondo)
        val binding = DialogTerminalBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // Título de la descripción
        val preTitle = ContextCompat.getString(this, R.string.dialog_terminal_pre_title)
        binding.dialogTerminalTitle.text = "$preTitle ${terminal.nombre}"

        // Color principal de la estación
        val color = ContextCompat.getColor(this, COLOR_GETTER.getColorID(terminal.lineas[0]))
        binding.dialogTerminalImage.setColorFilter(color)

        // Recycler view que muestra las líneas a las que pertenece la estación descrita
        val recyclerDialogTerminalAdapter = DialogTerminalAdapter()
        binding.dialogTerminalRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerDialogTerminalAdapter
        }
        recyclerDialogTerminalAdapter.addDataToList(terminal.lineas)

        binding.dialogTerminalButtonBack.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    //Se sobreescribe el uso de onBackPressed para evitar que se pueda volver a la selección de estaciones
    // ya que puede generar errores si no se inicia nuevamente la Activity
    override fun onBackPressed() {
        eraseBackStack()
        super.onBackPressed()
    }
}