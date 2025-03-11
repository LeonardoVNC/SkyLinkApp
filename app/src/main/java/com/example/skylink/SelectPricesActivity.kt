package com.example.skylink

import android.os.Bundle
import android.widget.Toast
import com.example.skylink.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.singletons.CompanionObjects.Companion.ID_SELECTED_PRICE
import com.example.skylink.databinding.ActivitySelectPricesBinding

class SelectPricesActivity : BaseActivity() {
    private lateinit var binding: ActivitySelectPricesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectPricesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        binding.selectPriceButtonBack.setOnClickListener{ onBackPressed() }
        binding.selectPriceCommonButton.setOnClickListener{
            sharedPreferences.edit().putString(ID_SELECTED_PRICE, "Estándar").apply()
            Toast.makeText(this, "Seleccionado el precio estándar", Toast.LENGTH_SHORT).show()
        }
        binding.selectPriceStudentButton.setOnClickListener{
            sharedPreferences.edit().putString(ID_SELECTED_PRICE, "Estudiante").apply()
            Toast.makeText(this, "Seleccionado el precio estudiantil", Toast.LENGTH_SHORT).show()
        }
        binding.selectPriceAdultButton.setOnClickListener{
            sharedPreferences.edit().putString(ID_SELECTED_PRICE, "Adulto mayor").apply()
            Toast.makeText(this, "Seleccionado el precio de adulto mayor", Toast.LENGTH_SHORT).show()
        }
    }
}