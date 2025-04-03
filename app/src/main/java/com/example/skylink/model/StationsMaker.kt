package com.example.skylink.model

import android.content.Context
import com.example.skylink.R
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.singletons.CompanionObjects.Companion.ASSET_READER

class StationsMaker() {
    var mapIDs = mutableMapOf<String, Int>(
        "azul" to R.color.azul,
        "rojo" to R.color.roja,
        "naranja" to R.color.naranja,
        "blanco" to R.color.blanca,
        "cafe" to R.color.cafe,
        "celeste" to R.color.celeste,
        "verde" to R.color.verde,
        "amarillo" to R.color.amarilla,
        "plateado" to R.color.plateada,
        "morado" to R.color.morada
    )

    fun loadStationList (context: Context): List<Estacion> {
        val data = ASSET_READER.loadStations(context)
        val listEstaciones = mutableListOf<Estacion>();
        for (i in data.indices) {
            val nombre = data[i][0]
            val colores = mutableListOf<Int>();
            for (j in 1..<data[i].size) {
                val colorID: Int = mapIDs.get(data[i][j]) ?: -1
                if (colorID == -1 ) {
                    println("Error en la carga de estaciones. MapID")
                    return listOf();
                }
                colores.add(colorID)
            }
            listEstaciones.add(Estacion(nombre, colores, i))
        }
        return listEstaciones
    }
}