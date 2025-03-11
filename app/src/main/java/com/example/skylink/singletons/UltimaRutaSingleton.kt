package com.example.skylink.singletons

import android.content.Context
import com.example.skylink.ProxyUltimaRuta
import com.example.skylink.observerPattern.PriceObserver

class UltimaRutaSingleton : PriceObserver {
    private var instanciado = false
    private lateinit var instance: ProxyUltimaRuta

    fun getInstance (context: Context): ProxyUltimaRuta {
        if (!instanciado) {
            instance = ProxyUltimaRuta(context)
            instanciado = true
        }
        return instance
    }

    @Override
    override fun update() {
        //Se debe borrar el registro de la última ruta para no confundir precios
        instanciado = false
    }
}