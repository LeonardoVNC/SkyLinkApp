package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.model.singletons.CompanionObjects.Companion.STATIONS_MAKER
import com.example.skylink.viewmodel.adapters.EstacionesAdapter
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.databinding.ActivitySelectTerminalBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK

//Activity para seleccionar las estaciones de origen y objetivo
class SelectTerminalActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivitySelectTerminalBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }
    private var begin: Boolean = true
    private var inputBegin: Int = -1
    private var inputEnd: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTerminalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //Cargamos el contenido del recyclerview para mostrar todas las estaciones
        setUpRecyclerView()

        //Configuración de botones y clicks
        binding.terminalButtonBack.setOnClickListener{
            if (begin) {
                onBackPressed()
            } else {
                setBeginInput()
            }
        }
    }

    //Al hacer click en uno de los items, se guarda su contenido en los nodos a usar
    override fun onItemClick(input: Int) {
        if (begin) {
            inputBegin = input
            setEndInput()
            Toast.makeText(this, "Seleccionada la estación de origen", Toast.LENGTH_SHORT).show()
        } else {
            inputEnd = input
            if (inputBegin == inputEnd) {
                Toast.makeText(this, "Ya se encuentra en su destino.", Toast.LENGTH_SHORT).show()
                eraseBackStack()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                Toast.makeText(this, "Seleccionada la estación objetivo", Toast.LENGTH_SHORT).show()
                intent.putExtra(ID_INPUT_BEGIN, inputBegin)
                intent.putExtra(ID_INPUT_END, inputEnd)
                intent.putExtra(ID_LLAMADA_SKYLINK, "Optimizar")
                startActivity(intent)
            }
        }
    }

    //Establece las variables para introducir el primer input, la estacion de origen
    private fun setBeginInput () {
        inputBegin = -1
        begin = true
        binding.terminalDescription.text = getString(R.string.terminal_text_begin)
    }

    //Establece las variables para introducir el segundo input, la estación destino
    private fun setEndInput () {
        inputEnd = -1
        begin = false
        binding.terminalDescription.text = getString(R.string.terminal_text_end)
    }

    //Valor usado para ordenar las estaciones por color
    val lineaConPrioridad = mapOf(
        R.color.roja to 1,
        R.color.amarilla to 2,
        R.color.verde to 3,
        R.color.celeste to 4,
        R.color.blanca to 5,
        R.color.morada to 6,
        R.color.naranja to 7,
        R.color.azul to 8,
        R.color.cafe to 9,
        R.color.plateada to 10
    )

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf<Estacion>()
        listaDeDatos.addAll(STATIONS_MAKER.loadTerminalList(this))
        //Se ordenan las estaciones por su color principal
        listaDeDatos.sortWith(compareBy { lineaConPrioridad[COLOR_GETTER.getColorID(it.lineas[0])] ?: Int.MAX_VALUE })
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.terminalRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }
}