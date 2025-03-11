package com.example.skylink

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.skylink.Singletons.Companion.APP_PREFERENCES
import com.example.skylink.Singletons.Companion.ID_USER_TYPE
import com.example.skylink.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        var clicks = 0

        binding.mainButtonMenu.setOnClickListener{
            val intent = Intent(this, ConfiguracionesActivity::class.java)
            startActivity(intent)
        }

        binding.mainButtonTime.setOnClickListener{
            val intent = Intent(this, SelectTerminalActivity::class.java)
            startActivity(intent)
        }

        binding.mainImage.setOnClickListener{
            if (clicks == 7) {
                sharedPreferences.edit().putInt(ID_USER_TYPE, 1).apply()
                Toast.makeText(this, "Opciones de desarrollador activadas", Toast.LENGTH_SHORT).show()
            } else {
                clicks++
            }
        }
    }
}