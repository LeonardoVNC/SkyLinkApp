package com.example.skylink

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivitySelectTerminalBinding

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
        } else {
            inputEnd = input
            //TODO iniciar el cálculo de los grafos
            println("Se debería dar inicio a Dijkstra con los nodos $inputBegin y $inputEnd")
            val intent = Intent(this, ResultActivity::class.java)
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

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf(
            Estacion(nombre = "Rio Seco", color = R.color.azul,0),
            Estacion(nombre = "UPEA", color = R.color.azul,1),
            Estacion(nombre = "Plaza La Paz", color = R.color.azul, 2),
            Estacion(nombre = "Plaza Libertad", color = R.color.azul, 3),
            Estacion(nombre = "16 de Julio", color = R.color.roja, 4),
            Estacion(nombre = "Cementerio", color = R.color.roja, 5),
            Estacion(nombre = "Central", color = R.color.roja, 6),
            Estacion(nombre = "Armentia", color = R.color.naranja, 7),
            Estacion(nombre = "Periferica", color = R.color.naranja, 8),
            Estacion(nombre = "Villarroel", color = R.color.blanca, 9),
            Estacion(nombre = "Busch", color = R.color.blanca, 10),
            Estacion(nombre = "Triangular", color = R.color.blanca, 11),
            Estacion(nombre = "Del Poeta", color = R.color.celeste, 12),
            Estacion(nombre = "Las Villas", color = R.color.cafe, 13),
            Estacion(nombre = "El Prado", color = R.color.celeste, 14),
            Estacion(nombre = "Teatro al Aire Libre", color = R.color.celeste, 15),
            Estacion(nombre = "Libertador", color = R.color.amarilla, 16),
            Estacion(nombre = "Alto Obrajes", color = R.color.verde, 17),
            Estacion(nombre = "Obrajes", color = R.color.verde, 18),
            Estacion(nombre = "Irpavi", color = R.color.verde, 19),
            Estacion(nombre = "Sopocachi", color = R.color.amarilla, 20),
            Estacion(nombre = "Buenos Aires", color = R.color.amarilla, 21),
            Estacion(nombre = "Mirador", color = R.color.amarilla, 22),
            Estacion(nombre = "6 de Marzo", color = R.color.morada, 23),
            Estacion(nombre = "Faro Murillo", color = R.color.morada, 24),
            Estacion(nombre = "Obelisco", color = R.color.morada, 25),
        )
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.terminalRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }
}