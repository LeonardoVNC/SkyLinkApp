package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.SELECTED_THEME
import com.example.skylink.databinding.ActivitySelectThemeBinding

class SelectThemeActivity : BaseActivity() {
    private lateinit var binding: ActivitySelectThemeBinding

    private var tema: Int = R.style.Theme_Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectThemeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        tema = sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_Default)

        //Para cualquier tema que se agregue a la aplicación, se debe añadir su configuracion aqui.
        binding.selectThemeDefault.setOnClickListener {
            tema = R.style.Theme_Default
            binding.selectThemeDescSave.text = getString(R.string.theme_default)
            binding.selectThemeDescSave.visibility = View.VISIBLE
        }
        binding.selectThemeDarkTur.setOnClickListener {
            tema = R.style.Theme_DarkTur
            binding.selectThemeDescSave.text = getString(R.string.theme_dark_tur)
            binding.selectThemeDescSave.visibility = View.VISIBLE
        }
        binding.selectThemeDarkPurple.setOnClickListener {
            tema = R.style.Theme_DarkPurple
            binding.selectThemeDescSave.text = getString(R.string.theme_dark_purple)
            binding.selectThemeDescSave.visibility = View.VISIBLE
        }
        binding.selectThemeDark.setOnClickListener {
            tema = R.style.Theme_Dark
            binding.selectThemeDescSave.text = getString(R.string.theme_dark)
            binding.selectThemeDescSave.visibility = View.VISIBLE
        }
        binding.selectThemeLight.setOnClickListener {
            tema = R.style.Theme_Light
            binding.selectThemeDescSave.text = getString(R.string.theme_light)
            binding.selectThemeDescSave.visibility = View.VISIBLE
        }

        binding.selectThemeButtonSave.setOnClickListener {
            sharedPreferences.edit().putInt(SELECTED_THEME, tema).apply()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK     //Se limpia el BackStack
            startActivity(intent)
        }

        binding.selectThemeButtonBack.setOnClickListener { onBackPressed() }
    }
}