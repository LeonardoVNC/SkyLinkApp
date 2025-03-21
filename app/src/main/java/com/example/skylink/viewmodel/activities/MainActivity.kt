package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_USER_TYPE
import com.example.skylink.databinding.ActivityMainBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK
import com.example.skylink.model.singletons.CompanionObjects.Companion.LAST_ROUTE_SINGLETON

//Activity para el menú principal de la aplicación
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        var clicks = 0

        //Configuración de botones y clicks
        binding.mainImage.setOnClickListener {
            //Dando 7 click a la imágen de la pantalla principal se activan las opciones de desarrollador
            if (clicks == 7) {
                sharedPreferences.edit().putInt(ID_USER_TYPE, 1).apply()
                Toast.makeText(this, "Opciones de desarrollador activadas", Toast.LENGTH_SHORT)
                    .show()
            } else {
                clicks++
            }
        }
        binding.mainOptionRoute.setOnClickListener {
            val intent = Intent(this, SelectTerminalActivity::class.java)
            startActivity(intent)
        }
        binding.mainOptionPrice.setOnClickListener {
            val intent = Intent(this, SelectPricesActivity::class.java)
            startActivity(intent)
        }
        binding.mainOptionLast.setOnClickListener {
            if (LAST_ROUTE_SINGLETON.getInstance(this).isCached()) {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ID_LLAMADA_SKYLINK, "Recargar")
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Sin registro de optimización en la sesión actual",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.mainOptionConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracionesActivity::class.java)
            startActivity(intent)
        }
    }
}