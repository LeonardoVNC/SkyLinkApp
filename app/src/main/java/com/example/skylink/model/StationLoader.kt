package com.example.skylink.model

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion

interface StationLoader {
    fun loadStationList (context: Context): List<Estacion>
}