package com.example.skylink

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding

class ResultActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivityResultBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //TODO aqui hacer los cálculos del algoritmo

        setUpRecyclerView()

        binding.resultButtonReset.setOnClickListener {
            //TODO reiniciar los parámetros de esta vista
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        //TODO  la lista debe ser proporcionada desde el algoritmo, esta es de ejemplo
        val listaDeDatos = mutableListOf(
            Estacion(nombre = "Rio Seco", color = R.color.azul, 0),
            Estacion(nombre = "UPEA", color = R.color.azul, 1),
            Estacion(nombre = "Plaza La Paz", color = R.color.azul, 2),
            Estacion(nombre = "Plaza Libertad", color = R.color.azul, 3),
            Estacion(nombre = "16 de Julio", color = R.color.roja, 4),
            Estacion(nombre = "Cementerio", color = R.color.roja, 5),
            Estacion(nombre = "Central", color = R.color.roja, 6)
        )
        recyclerTerminalAdapter.addDataToList(listaDeDatos)
        binding.resultRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerTerminalAdapter
        }
    }

    override fun onItemClick(input: Int) {}
}