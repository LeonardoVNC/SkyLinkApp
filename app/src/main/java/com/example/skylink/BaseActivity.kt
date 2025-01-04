package com.example.skylink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//Esta Activity sirve para aplicar correctamente los themes en el resto de Activities, sirve
// extendiendo a la Activity en lugar de AppCompatActivity. Cualquier Activity que requiera aplicar
// el theme seleccionado por el usuario debe extender de BaseActivity, si no se desea aplicar los
// temas, se puede mantener AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    companion object {
        val APP_PREFERENCES = "app_preferences"
        val SELECTED_THEME = "selected_theme"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val savedTheme = sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_DarkTur)
        setTheme(savedTheme)
        super.onCreate(savedInstanceState)
    }
}