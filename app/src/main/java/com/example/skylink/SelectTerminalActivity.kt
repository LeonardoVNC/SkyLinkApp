package com.example.skylink

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivitySelectTerminalBinding

class SelectTerminalActivity : BaseActivity() {
    private lateinit var binding: ActivitySelectTerminalBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTerminalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()

        binding.terminalButtonBack.setOnClickListener{onBackPressed()}
    }

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf(
            Estacion(nombre = "Central", color = R.color.roja),
            Estacion(nombre = "Cementerio", color = R.color.roja),
            Estacion(nombre = "16 de Julio", color = R.color.roja),
            Estacion(nombre = "Satélite", color = R.color.amarilla),
            Estacion(nombre = "Buenos Aires", color = R.color.amarilla),
            Estacion(nombre = "Cotahuma", color = R.color.amarilla),
            Estacion(nombre = "Libertador", color = R.color.amarilla),
            Estacion(nombre = "Alto Obrajes", color = R.color.verde),
            Estacion(nombre = "Obrajes", color = R.color.verde),
            Estacion(nombre = "Irpavi", color = R.color.verde),
            Estacion(nombre = "Armentia", color = R.color.naranja),
            Estacion(nombre = "Periferica", color = R.color.naranja),
            Estacion(nombre = "Villarroel", color = R.color.blanca),
            Estacion(nombre = "Busch", color = R.color.blanca),
            Estacion(nombre = "Triangular", color = R.color.blanca),
            Estacion(nombre = "Del Poeta", color = R.color.celeste),
            Estacion(nombre = "El Prado", color = R.color.celeste),
            Estacion(nombre = "Teatro al Aire Libre", color = R.color.celeste),
            Estacion(nombre = "Las Villas", color = R.color.cafe),
            Estacion(nombre = "6 de Marzo", color = R.color.morada),
            Estacion(nombre = "Faro Murillo", color = R.color.morada),
            Estacion(nombre = "Obelisco", color = R.color.morada),
            Estacion(nombre = "Plaza Libertad", color = R.color.azul),
            Estacion(nombre = "Plaza La Paz", color = R.color.azul),
            Estacion(nombre = "UPEA", color = R.color.azul),
            Estacion(nombre = "Rio Seco", color = R.color.azul)
        )
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.terminalRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }
}