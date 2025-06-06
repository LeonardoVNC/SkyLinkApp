package com.example.skylink.viewmodel.activities

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.R
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.SELECTED_THEME
import com.example.skylink.databinding.ActivitySelectThemeBinding
import com.example.skylink.model.dataClasses.Tema
import com.example.skylink.viewmodel.adapters.TemasAdapter
import com.example.skylink.viewmodel.clickListeners.OnThemeClickListener

//Activity que sirve para seleccionar el tema usado en la app
class SelectThemeActivity : BaseActivity(), OnThemeClickListener {
    private lateinit var binding: ActivitySelectThemeBinding
    private val recyclerThemeAdapter by lazy { TemasAdapter(this) }
    //Por defecto se usa el tema Default
    private var tema: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectThemeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()

        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        tema = sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_Default)

        //Configuración de botones y clicks
        binding.selectThemeButtonSave.setOnClickListener {
            sharedPreferences.edit().putInt(SELECTED_THEME, tema).apply()
            println("Ahora es ${sharedPreferences.getInt(SELECTED_THEME, R.style.Theme_Default)}")
            eraseBackStack()
        }
        binding.selectThemeButtonBack.setOnClickListener { onBackPressed() }
    }

    //Función que carga la información de los temas
    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf(
            Tema ( R.style.Theme_Light, getString(R.string.theme_light),
                getColor(R.color.themeLightPrimary),
                getColor(R.color.themeLightSecondary),
                getColor(R.color.themeLightAccent),
                getColor(R.color.themeLightSurface),
                getColor(R.color.fontLight)),
            Tema ( R.style.Theme_Default, getString(R.string.theme_default),
                getColor(R.color.themeDefaultPrimary),
                getColor(R.color.themeDefaultSecondary),
                getColor(R.color.themeDefaultAccent),
                getColor(R.color.themeDefaultSurface),
                getColor(R.color.fontLight)),
            Tema ( R.style.Theme_Dark, getString(R.string.theme_dark),
                getColor(R.color.themeDarkPrimary),
                getColor(R.color.themeDarkSecondary),
                getColor(R.color.themeDarkAccent),
                getColor(R.color.themeDarkSurface),
                getColor(R.color.fontDark)),
            Tema ( R.style.Theme_DarkTur, getString(R.string.theme_dark_tur),
                getColor(R.color.themeDarkTurPrimary),
                getColor(R.color.themeDarkTurSecondary),
                getColor(R.color.themeDarkTurAccent),
                getColor(R.color.themeDarkTurSurface),
                getColor(R.color.fontDark)),
            Tema ( R.style.Theme_DarkPurple, getString(R.string.theme_dark_purple),
                getColor(R.color.themeDarkPurplePrimary),
                getColor(R.color.themeDarkPurpleSecondary),
                getColor(R.color.themeDarkPurpleAccent),
                getColor(R.color.themeDarkPurpleSurface),
                getColor(R.color.fontDark)),
        )
        recyclerThemeAdapter.addDataToList(listaDeDatos)
        binding.selectThemeRecycler.apply() {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerThemeAdapter
        }
    }

    //Al hacer click en uno de los items, se sobreescribe la información del tema seleccionado
    override fun onItemClick(theme: Tema) {
        println(theme.themeID)
        tema = theme.themeID
        binding.selectThemeDescSave.text = theme.nombre
        binding.selectThemeDescSave.visibility = View.VISIBLE
    }
}