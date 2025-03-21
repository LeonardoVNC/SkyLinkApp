package com.example.skylink.model.singletons

import android.content.Context
import com.example.skylink.model.SkyLink
import com.example.skylink.viewmodel.observerPattern.PriceObserver
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE

//Clase que se encarga de devolver una única instancia de SkyLink.java
class SkyLinkSingleton : PriceObserver {
    private var instanciado = false
    private lateinit var instance: SkyLink

    fun getInstance(context: Context): SkyLink {
        if (!instanciado) {
            val sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val precioSeleccionado = sharedPreferences.getString(ID_SELECTED_PRICE, "Estándar")
            instance = SkyLink(precioSeleccionado, context)
            instanciado = true
        }
        return instance
    }

    @Override
    override fun update() {
        //Se debe actualizar la instancia SkyLink con el nuevo precio para forzar un nuevo cálculo y no el uso de uno anterior
        instanciado = false
    }

}