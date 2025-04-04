package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion

//Clase encargada de guardar en cache la lista de estaciones para mantener buen rendimiento en la App
class ProxyStationsMaker : TerminalLoader {
    lateinit var stationsList: List<Estacion>
    var cached = false

    override fun loadTerminalList(context: Context): List<Estacion> {
        if (!cached) {
            stationsList = TerminalListMaker().loadTerminalList(context)
            cached = true
        }
        return stationsList
    }
}