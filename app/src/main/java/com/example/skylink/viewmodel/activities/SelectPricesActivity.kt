package com.example.skylink.viewmodel.activities

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.R
import com.example.skylink.viewmodel.clickListeners.OnPriceClickListener
import com.example.skylink.viewmodel.adapters.PreciosAdapter
import com.example.skylink.model.dataClasses.Precios
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE
import com.example.skylink.databinding.ActivitySelectPricesBinding
import com.example.skylink.databinding.DialogConfirmationBinding
import com.example.skylink.viewmodel.observerPattern.PriceObserver
import com.example.skylink.viewmodel.observerPattern.PriceSubject
import com.example.skylink.model.singletons.CompanionObjects.Companion.ASSET_READER
import com.example.skylink.model.singletons.CompanionObjects.Companion.LAST_ROUTE_SINGLETON
import com.example.skylink.model.singletons.CompanionObjects.Companion.SKYLINK_SINGLETON

//Activity para la selección de los precios disponibles
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

        //Se agregan los observers para notificarles cuando se cambien los precios
        registerObserver(SKYLINK_SINGLETON)
        registerObserver(LAST_ROUTE_SINGLETON)

        //Configuración de botones y clicks
        binding.selectPriceButtonBack.setOnClickListener{ onBackPressed() }
    }

    //Función que carga todos los items del RecyclerView
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf<Precios>()
        listaDeDatos.addAll(ASSET_READER.readPriceData(this))
        recyclerPriceAdapter.addDataToList(listaDeDatos)
        binding.selectPriceRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerPriceAdapter
        }
    }

    //Implementación del patrón Observer
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

    //Al hacer click en uno de los items, se sobreescribe la información del precio usado en la app
    override fun onItemClick(titulo: String) {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        sharedPreferences.edit().putString(ID_SELECTED_PRICE, titulo).apply()

        // Configuración del Dialog
        val dialog = Dialog(this, R.style.DialogSinFondo)
        val binding = DialogConfirmationBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        // Descripción del dialog
        val preDesc = ContextCompat.getString(this, R.string.dialog_confirm_text_price)
        binding.dialogConfirmDesc.text = "$preDesc\n$titulo"
        binding.dialogConfirmButtonAccept.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()

        //También se notifica a los observers, ya que el cambio en precios genera cambio en
        // el monto de las rutas ya calculadas
        notifyObservers()
    }
}