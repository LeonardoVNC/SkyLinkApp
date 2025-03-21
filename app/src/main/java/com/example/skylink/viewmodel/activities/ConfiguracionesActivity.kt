package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_USER_TYPE
import com.example.skylink.databinding.ActivityConfiguracionesBinding

//Activity usada para mostrar las opciones de configuración disponibles
class ConfiguracionesActivity : BaseActivity() {
    private lateinit var binding: ActivityConfiguracionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Si las opciones de desarrollador están activadas, se muestra la opción para entrar a dicho menú
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        if (sharedPreferences.getInt(ID_USER_TYPE, 0) == 1) {
            binding.configDev.visibility = View.VISIBLE
        }

        //Configuración de botones y clicks
        binding.configButtonBack.setOnClickListener { onBackPressed() }
        binding.configButtonPrice.setOnClickListener {
            val intent = Intent(this, SelectPricesActivity::class.java)
            startActivity(intent)
        }
        binding.configButtonTheme.setOnClickListener {
            val intent = Intent(this, SelectThemeActivity::class.java)
            startActivity(intent)
        }
        binding.configButtonDev.setOnClickListener {
            val intent = Intent(this, DevActivity::class.java)
            startActivity(intent)
        }
    }
}