package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.customDataStructures.RespuestaOptimizador
import com.example.skylink.model.singletons.CompanionObjects.Companion.SKYLINK_SINGLETON

class ProxyUltimaRuta(private val context: Context):
    Optimizador {
    private var lastBegin = -1
    private var lastEnd = -1
    private lateinit var lastOptimization: RespuestaOptimizador
    private var cached = false

    @Override
    override fun optimizarRuta(nodoInicial: Int, nodoObjetivo: Int): RespuestaOptimizador {
        if ((lastBegin == nodoInicial && lastEnd == nodoObjetivo)||(lastEnd == nodoInicial && lastBegin == nodoObjetivo)) {
            println("Ruta obtenida desde cache, ahorrado el proceso ;D")
            return lastOptimization
        } else {
            lastOptimization = SKYLINK_SINGLETON.getInstance(context).optimizarRuta(nodoInicial, nodoObjetivo)
            cached = true
            lastBegin = nodoInicial
            lastEnd = nodoObjetivo
            return lastOptimization
        }
    }

    fun isCached(): Boolean { return cached }

    fun getLastOptmization(): RespuestaOptimizador { return lastOptimization }
}