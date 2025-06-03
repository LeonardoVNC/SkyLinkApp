package com.example.skylink.model.dataReader

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.dataClasses.Precios

interface DataReader {
    fun getGraphInfo (context: Context): MutableList<List<Int>>
    fun readPriceData (context: Context): List<Precios>
    fun readTerminalData (context: Context): List<Estacion>
}