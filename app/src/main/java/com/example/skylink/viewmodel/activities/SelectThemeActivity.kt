package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.SELECTED_THEME
import com.example.skylink.databinding.ActivitySelectThemeBinding

//Activity que sirve para seleccionar el tema usado en la app
class SelectThemeActivity : BaseActivity() {
    private lateinit var binding: ActivitySelectThemeBinding
    //Por defecto se usa el tema Default
    private var tema: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectThemeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        tema = sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_Default)

        //Configuración de botones y clicks
        binding.selectThemeButtonSave.setOnClickListener {
            sharedPreferences.edit().putInt(SELECTED_THEME, tema).apply()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }
        binding.selectThemeButtonBack.setOnClickListener { onBackPressed() }

        //Para cualquier tema que se agregue a la aplicación, se debe añadir su configuracion aqui:
        binding.selectThemeDefault.setOnClickListener {
            setTheme(R.style.Theme_Default, getString(R.string.theme_default))
        }
        binding.selectThemeDarkTur.setOnClickListener {
            setTheme(R.style.Theme_DarkTur, getString(R.string.theme_dark_tur))
        }
        binding.selectThemeDarkPurple.setOnClickListener {
            setTheme(R.style.Theme_DarkPurple, getString(R.string.theme_dark_purple))
        }
        binding.selectThemeDark.setOnClickListener {
            setTheme(R.style.Theme_Dark, getString(R.string.theme_dark))
        }
        binding.selectThemeLight.setOnClickListener {
            setTheme(R.style.Theme_Light, getString(R.string.theme_light))
        }
    }

    //Funcion para factorizar el cambio de temas
    fun setTheme(tema: Int, desc: String) {
        this.tema = tema
        binding.selectThemeDescSave.text = desc
        binding.selectThemeDescSave.visibility = View.VISIBLE
    }
}