package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion

//Interfaz que debe implementar cualquier clase que busque crear la lista de estaciones
interface TerminalLoader {
    fun loadTerminalList (context: Context): List<Estacion>
}