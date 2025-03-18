package com.example.skylink

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.adapters.PreciosAdapter
import com.example.skylink.dataClasses.Precios
import com.example.skylink.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE
import com.example.skylink.databinding.ActivitySelectPricesBinding
import com.example.skylink.observerPattern.PriceObserver
import com.example.skylink.observerPattern.PriceSubject
import com.example.skylink.singletons.CompanionObjects.Companion.LAST_ROUTE_SINGLETON
import com.example.skylink.singletons.CompanionObjects.Companion.LIST_ESTACIONES
import com.example.skylink.singletons.CompanionObjects.Companion.SKYLINK_SINGLETON
import java.io.BufferedReader
import java.io.InputStreamReader

class SelectPricesActivity : BaseActivity() , PriceSubject, OnPriceClickListener {
    private lateinit var binding: ActivitySelectPricesBinding
    private val recyclerPriceAdapter by lazy { PreciosAdapter(this) }
    private val observers = mutableListOf<PriceObserver>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPricesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()

        registerObserver(SKYLINK_SINGLETON)
        registerObserver(LAST_ROUTE_SINGLETON)

        binding.selectPriceButtonBack.setOnClickListener{ onBackPressed() }
    }

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf<Precios>()
        listaDeDatos.addAll(loadItemsFromAssets(this))
        recyclerPriceAdapter.addDataToList(listaDeDatos)
        binding.selectPriceRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerPriceAdapter
        }
    }

    fun loadItemsFromAssets(context: Context): List<Precios> {
        val listaPrecios = mutableListOf<Precios>()
        val inputStream = context.assets.open("precios.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use { br ->
            br.forEachLine { line ->
                val parts = line.split(",")
                if (parts.size == 4) {
                    val titulo = parts[0]
                    val descripcion = parts[1]
                    val montoA = parts[2].toDoubleOrNull() ?: 0.0
                    val montoB = parts[3].toDoubleOrNull() ?: 0.0
                    listaPrecios.add(Precios(titulo, descripcion, montoA, montoB))
                }
            }
        }

        return listaPrecios
    }

    @Override
    override fun registerObserver(observer: PriceObserver) {
        observers.add(observer)
    }

    @Override
    override fun removeObserver(observer: PriceObserver) {
        observers.remove(observer)
    }

    @Override
    override fun notifyObservers() {
        observers.forEach() {
            it.update()
        }
    }

    override fun onItemClick(titulo: String) {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        sharedPreferences.edit().putString(ID_SELECTED_PRICE, titulo).apply()
        Toast.makeText(this, "Seleccionado precio para: ${titulo}", Toast.LENGTH_SHORT).show()
        notifyObservers()
    }
}