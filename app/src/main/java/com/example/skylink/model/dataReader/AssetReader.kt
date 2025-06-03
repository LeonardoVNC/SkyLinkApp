package com.example.skylink.model.dataReader

import android.content.Context
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.dataClasses.Precios
import java.io.BufferedReader
import java.io.InputStreamReader

//Clase uada para la lectura de datos guardados en los distintos assets
class AssetReader() : DataReader {
    //Funcion que retorna la información de precios.txt
    override fun readPriceData(context: Context): List<Precios> {
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

    //Función que retorna la información correspondiente a la estructura del grafo usado por el Optimizador
    override fun getGraphInfo(context: Context): MutableList<List<Int>> {
        val estructuraGrafo = mutableListOf<List<Int>>()
        val inputStream = context.assets.open("grafoTeleferico.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.use { br ->
            //Se lee la información básica del grafo, como el número de nodos y estaciones
            val primeraLinea = br.readLine()
            estructuraGrafo.add(primeraLinea.split(" ").map { it.toInt() })

            //Bucle para leer la información de las estaciones y sus líneas correspondientes
            var siguienteLinea = br.readLine()
            while (siguienteLinea.isNotEmpty()) {
                val parts = siguienteLinea.split(" ")
                if (parts.size != 2) {
                    break;
                }
                val colorLinea = parts.map { it.toInt() }
                estructuraGrafo.add(colorLinea)
                siguienteLinea = br.readLine()
            }
            //Bucle para leer la información de las aristas entre las estaciones y su peso (tiempo) correspondiente
            while (siguienteLinea != null && siguienteLinea.isNotEmpty()) {
                val parts = siguienteLinea.split(" ")
                if (parts.size != 3) {
                    break;
                }
                val arista = parts.map { it.toInt() }
                estructuraGrafo.add(arista)
                siguienteLinea = br.readLine()
            }
        }
        return estructuraGrafo
    }

    //Funcion que retorna la información de estaciones.txt
    override fun readTerminalData(context: Context): List<Estacion> {
        val data = mutableListOf<Estacion>()
        val inputStream = context.assets.open("estaciones.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        var currentID = 0
        reader.use { br ->
            br.forEachLine { line ->
                val parts = line.split(",")
                val nombre = parts[0]
                val lineas = mutableListOf<String>()
                for (i in 1..<parts.size) {
                    lineas.add(parts[i])
                }
                data.add(Estacion(nombre, lineas, currentID++))
            }
        }
        return data
    }
}