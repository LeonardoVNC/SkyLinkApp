package com.example.skylink

import android.content.Intent
import android.os.Bundle
import com.example.skylink.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.mainButtonMenu.setOnClickListener{
            val intent = Intent(this, SelectThemeActivity::class.java)
            startActivity(intent)
        }

        binding.mainButtonTime.setOnClickListener{
            //TODO acceder al menú de selección de estaciones
            val intent = Intent(this, SelectTerminalActivity::class.java)
            startActivity(intent)
        }

        binding.mainButtonPrice.setOnClickListener{
            //TODO pensar si acceder al selector de tipo de cliente o si eso se define dentro de las configuraciones
        }
    }
}