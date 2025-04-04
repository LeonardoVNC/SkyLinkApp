package com.example.skylink.model.dataReader

import com.example.skylink.R

class ColorGetter {
    var mapColorToID = mutableMapOf(
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

    fun getColorID(key: String): Int {
        return mapColorToID[key] ?: -1
    }
}