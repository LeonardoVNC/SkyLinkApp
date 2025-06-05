package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.singletons.CompanionObjects.Companion.ASSET_READER

//Clase encargada de crear la lista de Estaciones
class TerminalListMaker() : TerminalLoader {
    override fun loadTerminalList (context: Context): List<Estacion> {
        return ASSET_READER.readTerminalData(context)
    }
}