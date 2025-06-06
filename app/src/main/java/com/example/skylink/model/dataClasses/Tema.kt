package com.example.skylink.model.dataClasses

//Data class usada para guardar información correspondiente a los temas que se puedan seleccionar
data class Tema(
    val themeID: Int,
    val nombre: String,
    val colorPrimarioID: Int,
    val colorSecundarioID: Int,
    val colorAccentID: Int,
    val colorSurface: Int
)
