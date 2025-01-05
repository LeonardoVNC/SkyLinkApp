package com.example.skylink

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.SelectTerminalActivity.Companion.ID_INPUT_BEGIN
import com.example.skylink.SelectTerminalActivity.Companion.ID_INPUT_END
import com.example.skylink.adapters.EstacionesAdapter
import com.example.skylink.dataClasses.Estacion
import com.example.skylink.databinding.ActivityResultBinding
import java.util.LinkedList



class ResultActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivityResultBinding
    private val recyclerTerminalAdapter by lazy { EstacionesAdapter(this) }
    private var nodoInicial = -1
    private var nodoFinal = -1
    private var grafoCargado: Boolean = false
    private lateinit var grafo: Array<LinkedList<IntArray>>
    private lateinit var lineas: Array<MutableSet<Int>>

    private val INF: Int = Int.MAX_VALUE //Definimos infinito como el número más alto en java (para ints)
    private lateinit var dist: IntArray //Arreglo que guardará la distancia entre el nodo de inicio y el resto de nodos
    private lateinit var padreDijkstra: IntArray //Arreglo que guardará al nodo del que viene el camino con menor peso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        nodoInicial = intent.getIntExtra(ID_INPUT_BEGIN, -1)
        nodoFinal = intent.getIntExtra(ID_INPUT_END, -1)

        //Cargar el grafo si aun no ha sido iniciado
        if (!grafoCargado) {
            cargarGrafo()
            cargarLineas()
            grafoCargado=true
        }

        //Calcular el tiempo
        optimizarTiempo(nodoInicial, nodoFinal)

        //Calcular el precio

        //Mostrar la ruta recorrida
        setUpRecyclerView(mutableListOf(Estacion("Oli", R.color.azul, 4)))

        binding.resultButtonReset.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }
    }

    private fun cargarGrafo() {
        grafo[0].addAll(listOf(
            intArrayOf(1,7)
        ))
        grafo[1].addAll(listOf(
            intArrayOf(0,7), intArrayOf(2,5)
        ))
        grafo[2].addAll(listOf(
            intArrayOf(1,5), intArrayOf(3,4)
        ))
        grafo[3].addAll(listOf(
            intArrayOf(2,4), intArrayOf(4,4)
        ))
        grafo[4].addAll(listOf(
            intArrayOf(3,4), intArrayOf(5,5), intArrayOf(24,8)
        ))
        grafo[5].addAll(listOf(
            intArrayOf(4,5), intArrayOf(6,5)
        ))
        grafo[6].addAll(listOf(
            intArrayOf(5,5), intArrayOf(7,4)
        ))
        grafo[7].addAll(listOf(
            intArrayOf(6,4), intArrayOf(8,4)
        ))
        grafo[8].addAll(listOf(
            intArrayOf(7,4), intArrayOf(9,4)
        ))
        grafo[9].addAll(listOf(
            intArrayOf(8,4), intArrayOf(10,4)
        ))
        grafo[10].addAll(listOf(
            intArrayOf(9,4), intArrayOf(11,5), intArrayOf(13,4)
        ))
        grafo[11].addAll(listOf(
            intArrayOf(10,5), intArrayOf(12,3)
        ))
        grafo[12].addAll(listOf(
            intArrayOf(11,3), intArrayOf(16,3), intArrayOf(15,3)
        ))
        grafo[13].addAll(listOf(
            intArrayOf(10,4)
        ))
        grafo[14].addAll(listOf(
            intArrayOf(15,4)
        ))
        grafo[15].addAll(listOf(
            intArrayOf(12,3), intArrayOf(14,4)
        ))
        grafo[16].addAll(listOf(
            intArrayOf(12,3), intArrayOf(17,3), intArrayOf(20,6)
        ))
        grafo[17].addAll(listOf(
            intArrayOf(16,3), intArrayOf(18,5)
        ))
        grafo[18].addAll(listOf(
            intArrayOf(17,5), intArrayOf(19,8)
        ))
        grafo[19].addAll(listOf(
            intArrayOf(18,8)
        ))
        grafo[20].addAll(listOf(
            intArrayOf(16,6), intArrayOf(21,6)
        ))
        grafo[21].addAll(listOf(
            intArrayOf(20,6), intArrayOf(22,4)
        ))
        grafo[22].addAll(listOf(
            intArrayOf(21,4), intArrayOf(24,4)
        ))
        grafo[23].addAll(listOf(
            intArrayOf(24,8)
        ))
        grafo[24].addAll(listOf(
            intArrayOf(4,8), intArrayOf(22,4), intArrayOf(25,8), intArrayOf(23,8)
        ))
        grafo[25].addAll(listOf(
            intArrayOf(24,8)
        ))
    }

    private fun cargarLineas() {
        lineas[0].add(0)
        lineas[1].add(0)
        lineas[2].add(0)
        lineas[3].add(0)
        lineas[4].addAll(setOf(0,1,8))
        lineas[5].add(1)
        lineas[6].addAll(setOf(1,2))
        lineas[7].add(2)
        lineas[8].add(2)
        lineas[9].addAll(setOf(2,3))
        lineas[10].addAll(setOf(3,4))
        lineas[11].add(3)
        lineas[12].addAll(setOf(3,5))
        lineas[13].add(4)
        lineas[14].add(5)
        lineas[15].add(5)
        lineas[16].addAll(setOf(5,6,7))
        lineas[17].add(6)
        lineas[18].add(6)
        lineas[19].add(6)
        lineas[20].add(7)
        lineas[21].add(7)
        lineas[22].addAll(setOf(7,8))
        lineas[23].add(9)
        lineas[24].addAll(setOf(8,9))
        lineas[25].add(9)
    }


    private fun optimizarTiempo(nodoInicial : Int, nodoObjetivo: Int) {
        dist = IntArray(grafo.size)
        padreDijkstra = IntArray(grafo.size)
        dist.fill(INF)

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