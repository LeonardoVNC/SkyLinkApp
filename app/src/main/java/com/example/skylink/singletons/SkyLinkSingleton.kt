package com.example.skylink.singletons

import android.content.Context
import com.example.skylink.SkyLink
import com.example.skylink.observerPattern.PriceObserver
import com.example.skylink.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE

class SkyLinkSingleton : PriceObserver {
    private var instanciado = false
    private lateinit var instance: SkyLink

    fun getInstance (context: Context): SkyLink {
        if (!instanciado) {
            val sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            val precioSeleccionado = sharedPreferences.getString(ID_SELECTED_PRICE, "Estándar")
            instance = SkyLink(precioSeleccionado)
            instanciado=true
        }
        return instance
    }

    @Override
    override fun update() {
        //Se debe actualizar la instancia SkyLink con el nuevo precio
        instanciado = false
    }

}