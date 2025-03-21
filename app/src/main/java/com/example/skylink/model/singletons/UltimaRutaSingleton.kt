package com.example.skylink.model.singletons

import android.content.Context
import com.example.skylink.model.ProxyUltimaRuta
import com.example.skylink.viewmodel.observerPattern.PriceObserver

//Clase usada para devolver una única instancia de ProxyUltimaRuta
class UltimaRutaSingleton : PriceObserver {
    private var instanciado = false
    private lateinit var instance: ProxyUltimaRuta

    fun getInstance(context: Context): ProxyUltimaRuta {
        if (!instanciado) {
            instance = ProxyUltimaRuta(context)
            instanciado = true
        }
        return instance
    }

    @Override
    override fun update() {
        //Se debe borrar el registro de la última ruta para no confundir precios anteriores
        instanciado = false
    }
}