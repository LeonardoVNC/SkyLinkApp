package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.singletons.CompanionObjects.Companion.ASSET_READER

//Clase encargada de crear la lista de Estaciones
class TerminalListMaker() : TerminalLoader {
    override fun loadTerminalList (context: Context): List<Estacion> {
        val data = ASSET_READER.loadStations(context)
        val listEstaciones = mutableListOf<Estacion>();
        for (i in data.indices) {
            val nombre = data[i][0]
            val colores = mutableListOf<String>();
            for (j in 1..<data[i].size) {
                colores.add(data[i][j])
            }
            listEstaciones.add(Estacion(nombre, colores, i))
        }
        return listEstaciones
    }
}