package com.example.skylink.singletons

import com.example.skylink.BaseActivity
import com.example.skylink.SkyLink
import com.example.skylink.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE

class SkyLinkSingleton: BaseActivity() {
    private lateinit var instance: SkyLink;

    fun getInstance (): SkyLink {
        if (instance==null) {
            val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
            val precioSeleccionado = sharedPreferences.getString(ID_SELECTED_PRICE, "Estándar")
            instance = SkyLink(precioSeleccionado)
        }
            return instance
    }
}