package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion

class ProxyStationsMaker : StationLoader {
    lateinit var stationsList: List<Estacion>
    var cached = false

    override fun loadStationList(context: Context): List<Estacion> {
        if (!cached) {
            stationsList = StationsMaker().loadStationList(context)
            cached = true
        }
        return stationsList
    }
}