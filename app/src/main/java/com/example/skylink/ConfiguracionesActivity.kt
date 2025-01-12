package com.example.skylink

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.skylink.MainActivity.Companion.ID_USER_TYPE
import com.example.skylink.databinding.ActivityConfiguracionesBinding

class ConfiguracionesActivity : BaseActivity() {
    private lateinit var binding: ActivityConfiguracionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        if (sharedPreferences.getInt(ID_USER_TYPE, 0) == 1) {
            binding.configDev.visibility = View.VISIBLE
        }

        binding.configButtonBack.setOnClickListener{ onBackPressed() }
        binding.configButtonPrice.setOnClickListener{
            val intent = Intent(this, SelectPricesActivity::class.java)
            startActivity(intent)
        }
        binding.configButtonTheme.setOnClickListener{
            val intent = Intent(this, SelectThemeActivity::class.java)
            startActivity(intent)
        }
        binding.configButtonDev.setOnClickListener{
            val intent = Intent(this, DevActivity::class.java)
            startActivity(intent)
        }
    }
}