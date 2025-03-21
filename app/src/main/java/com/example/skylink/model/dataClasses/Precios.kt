package com.example.skylink.model.dataClasses

//Data class usada para guardar la información correspondiente a los diferentes precios disponibles
data class Precios(
    val titulo: String,
    val descripcion: String,
    val precioAbordaje: Double,
    val precioTransbordo: Double
)
