package com.example.skylink.model.singletons

import com.example.skylink.model.dataReader.LectorAssets
import com.example.skylink.model.StationsMaker

//Clase que acumula todos los Companion Objects de la aplicación para facilitar su acceso
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
        val STATIONS_MAKER = StationsMaker()
    }
}