package com.example.skylink.model.dataReader

import com.example.skylink.R

class ColorGetter {
    var mapColorToID = mutableMapOf(
        "azul" to R.color.azul,
        "roja" to R.color.roja,
        "naranja" to R.color.naranja,
        "blanca" to R.color.blanca,
        "cafe" to R.color.cafe,
        "celeste" to R.color.celeste,
        "verde" to R.color.verde,
        "amarilla" to R.color.amarilla,
        "plateada" to R.color.plateada,
        "morada" to R.color.morada
    )

    fun getColorID(key: String): Int {
        return mapColorToID[key] ?: -1
    }
}