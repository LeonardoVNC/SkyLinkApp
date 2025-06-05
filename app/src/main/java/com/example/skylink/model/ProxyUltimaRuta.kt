package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.customDataStructures.RespuestaOptimizador
import com.example.skylink.model.singletons.CompanionObjects.Companion.SKYLINK_SINGLETON

//Implementación del patrón Proxy que busca optimizar el manejo de solicitudes, guardando la más reciente
// en cache para su rápida recuperación o devolviendo esta misma si se realiza la misma solicitud 2 veces seguidas
class ProxyUltimaRuta(private val context: Context) : Optimizador {
    private var lastBegin = -1
    private var lastEnd = -1
    private lateinit var lastOptimization: RespuestaOptimizador
    private var cached = false

    override fun inicializarGrafo(datosGrafo: MutableList<MutableList<Int>>?) {
        SKYLINK_SINGLETON.getInstance(context).inicializarGrafo(datosGrafo)
    }

    @Override
    override fun optimizarRuta(estacionOrigen: Int, estacionObjetivo: Int): RespuestaOptimizador {
        //Si una nueva solicitud posee los mismo parámetros que la última realizada, se devuelve desde cache
        if ((lastBegin == estacionOrigen && lastEnd == estacionObjetivo) || (lastEnd == estacionOrigen && lastBegin == estacionObjetivo)) {
            println("Ruta obtenida desde cache, ahorrado el proceso ;D")
            return lastOptimization
        } else {
            //Caso contrario se pide la optimización a SkyLink, se guarda en cache y se devuelve la respuesta
            lastOptimization = SKYLINK_SINGLETON.getInstance(context).optimizarRuta(estacionOrigen, estacionObjetivo)
            cached = true
            lastBegin = estacionOrigen
            lastEnd = estacionObjetivo
            return lastOptimization
        }
    }

    fun isCached(): Boolean {
        return cached
    }

    fun getLastOptimization(): RespuestaOptimizador {
        return lastOptimization
    }
}