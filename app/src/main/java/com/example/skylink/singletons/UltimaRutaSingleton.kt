package com.example.skylink.singletons

import android.content.Context
import com.example.skylink.ProxyUltimaRuta

class UltimaRutaSingleton {
    private var instanciado = false
    private lateinit var instance: ProxyUltimaRuta

    fun getInstance (context: Context): ProxyUltimaRuta {
        if (!instanciado) {
            instance = ProxyUltimaRuta(context)
            instanciado = true
        }
        return instance
    }
}