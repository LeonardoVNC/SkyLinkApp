package com.example.skylink.model.singletons

import com.example.skylink.model.dataReader.LectorAssets
import com.example.skylink.R
import com.example.skylink.model.dataClasses.Estacion

class CompanionObjects {
    companion object {
        val APP_PREFERENCES = "app_preferences"
        val SELECTED_THEME = "selected_theme"
        val ID_USER_TYPE = "user_type"
        val ID_SELECTED_PRICE = "selected_price"
        val ID_INPUT_BEGIN = "INPUT_BEGIN"
        val ID_INPUT_END = "INPUT_END"
        val SKYLINK_SINGLETON = SkyLinkSingleton()
        val LAST_ROUTE_SINGLETON = UltimaRutaSingleton()
        val ID_LLAMADA_SKYLINK = "SKYLINK_CALL"
        val ASSET_READER = LectorAssets()
        val LIST_ESTACIONES = mutableListOf(
            Estacion(nombre = "Río Seco", color = R.color.azul,0),
            Estacion(nombre = "UPEA", color = R.color.azul,1),
            Estacion(nombre = "Plaza La Paz", color = R.color.azul, 2),
            Estacion(nombre = "Plaza Libertad", color = R.color.azul, 3),
            Estacion(nombre = "16 de Julio", color = R.color.roja, 4),
            Estacion(nombre = "Cementerio", color = R.color.roja, 5),
            Estacion(nombre = "Central", color = R.color.roja, 6),
            Estacion(nombre = "Armentia", color = R.color.naranja, 7),
            Estacion(nombre = "Periférica", color = R.color.naranja, 8),
            Estacion(nombre = "Villarroel", color = R.color.blanca, 9),
            Estacion(nombre = "Busch", color = R.color.blanca, 10),
            Estacion(nombre = "Triangular", color = R.color.blanca, 11),
            Estacion(nombre = "Del Poeta", color = R.color.celeste, 12),
            Estacion(nombre = "Las Villas", color = R.color.cafe, 13),
            Estacion(nombre = "El Prado", color = R.color.celeste, 14),
            Estacion(nombre = "Teatro al Aire Libre", color = R.color.celeste, 15),
            Estacion(nombre = "Libertador", color = R.color.amarilla, 16),
            Estacion(nombre = "Alto Obrajes", color = R.color.verde, 17),
            Estacion(nombre = "Obrajes", color = R.color.verde, 18),
            Estacion(nombre = "Irpavi", color = R.color.verde, 19),
            Estacion(nombre = "Sopocachi", color = R.color.amarilla, 20),
            Estacion(nombre = "Buenos Aires", color = R.color.amarilla, 21),
            Estacion(nombre = "Mirador", color = R.color.amarilla, 22),
            Estacion(nombre = "6 de Marzo", color = R.color.morada, 23),
            Estacion(nombre = "Faro Murillo", color = R.color.morada, 24),
            Estacion(nombre = "Obelisco", color = R.color.morada, 25),
        )
    }
}