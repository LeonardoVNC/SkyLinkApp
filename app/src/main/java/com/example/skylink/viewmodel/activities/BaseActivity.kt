package com.example.skylink.viewmodel.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.SELECTED_THEME

//Esta Activity sirve para aplicar correctamente los themes en el resto de Activities, sirve
// extendiendo a la Activity en lugar de AppCompatActivity. Cualquier Activity que requiera aplicar
// el theme seleccionado por el usuario debe extender de BaseActivity, si no se desea aplicar los
// temas, se puede mantener AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        val savedTheme = sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_Default)
        setTheme(savedTheme)
        super.onCreate(savedInstanceState)
    }
}