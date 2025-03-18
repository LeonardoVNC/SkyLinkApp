package com.example.skylink

import android.content.Context
import com.example.skylink.dataClasses.Precios
import java.io.BufferedReader
import java.io.InputStreamReader

class LectorAssets {
    fun loadPrices(context: Context): List<Precios> {
        val listaPrecios = mutableListOf<Precios>()
        val inputStream = context.assets.open("precios.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use { br ->
            br.forEachLine { line ->
                val parts = line.split(",")
                if (parts.size == 4) {
                    val titulo = parts[0]
                    val descripcion = parts[1]
                    val abordaje = parts[2].toDoubleOrNull() ?: 0.0
                    val transbordo = parts[3].toDoubleOrNull() ?: 0.0
                    listaPrecios.add(Precios(titulo, descripcion, abordaje, transbordo))
                }
            }
        }
        return listaPrecios
    }

    fun searchTitlePrices(context: Context, titulo: String): Array<String> {
        val inputStream = context.assets.open("precios.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use { br ->
            for (line in br.lines()) {
                val parts = line.split(",")
                if (parts.size == 4 && parts[0].equals(titulo)) {
                    return arrayOf(parts[0], parts[1], parts[2], parts[3])
                }
            }
        }
        return arrayOf("Error")
    }
}