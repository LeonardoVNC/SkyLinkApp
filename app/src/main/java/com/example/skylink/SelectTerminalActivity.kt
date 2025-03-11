package com.example.skylink

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.singletons.CompanionObjects.Companion.LIST_ESTACIONES
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivitySelectTerminalBinding
import com.example.skylink.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK

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
        setUpRecyclerView()

        binding.terminalButtonBack.setOnClickListener{
            if (begin) {
                onBackPressed()
            } else {
                setBeginInput()
            }
        }
    }

    override fun onItemClick(input: Int) {
        if (begin) {
            inputBegin = input
            setEndInput()
            Toast.makeText(this, "Seleccionada la estación de origen", Toast.LENGTH_SHORT).show()
        } else {
            inputEnd = input
            val intent = Intent(this, ResultActivity::class.java)
            Toast.makeText(this, "Seleccionada la estación objetivo", Toast.LENGTH_SHORT).show()
            intent.putExtra(ID_INPUT_BEGIN, inputBegin)
            intent.putExtra(ID_INPUT_END, inputEnd)
            intent.putExtra(ID_LLAMADA_SKYLINK, "Optimizar")
            startActivity(intent)
        }
    }

    private fun setBeginInput () {
        inputBegin = -1
        begin = true
        binding.terminalDescription.text = getString(R.string.terminal_text_begin)
    }

    private fun setEndInput () {
        inputEnd = -1
        begin = false
        binding.terminalDescription.text = getString(R.string.terminal_text_end)
    }

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
        listaDeDatos.addAll(LIST_ESTACIONES)
        listaDeDatos.sortWith(compareBy { lineaConPrioridad[it.color] ?: Int.MAX_VALUE })
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.terminalRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }
}