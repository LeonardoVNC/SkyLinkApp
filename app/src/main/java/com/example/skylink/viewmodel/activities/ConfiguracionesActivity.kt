package com.example.skylink.viewmodel.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.R
import com.example.skylink.databinding.ActivityConfiguracionesBinding
import com.example.skylink.model.dataClasses.ConfigOption
import com.example.skylink.model.singletons.CompanionObjects.Companion.APP_PREFERENCES
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_USER_TYPE
import com.example.skylink.viewmodel.adapters.ConfigurationAdapter
import com.example.skylink.viewmodel.clickListeners.OnConfigClickListener

//Activity usada para mostrar las opciones de configuración disponibles
class ConfiguracionesActivity : BaseActivity(), OnConfigClickListener {
    private lateinit var binding: ActivityConfiguracionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val listaDeDatos = mutableListOf(
            ConfigOption(
                getString(R.string.config_price),
                0,
                Intent(this, SelectPricesActivity::class.java)
            ),
            ConfigOption(
                getString(R.string.config_theme),
                0,
                Intent(this, SelectThemeActivity::class.java)
            ),
            ConfigOption(
                getString(R.string.config_dev),
                1,
                Intent(this, DevActivity::class.java)
            ),
        )
        val userType = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(ID_USER_TYPE, 0)
        val recyclerConfigurationAdapter by lazy { ConfigurationAdapter(this, userType) }
        recyclerConfigurationAdapter.addDataToList(listaDeDatos)
        binding.configRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recyclerConfigurationAdapter
        }
    }

    override fun onConfigClick(intent: Intent) = startActivity(intent)
}