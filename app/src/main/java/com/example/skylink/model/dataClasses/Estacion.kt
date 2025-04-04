package com.example.skylink.model.dataClasses

//Data class usada para guardar información correspondiente a las estaciones del teleférico
data class Estacion(
    val nombre: String,
    val lineas: List<String>,
    val inputID: Int
)
