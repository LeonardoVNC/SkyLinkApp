package com.example.skylink

import android.os.Bundle
import android.view.View
import com.example.skylink.databinding.ActivityDevBinding

class DevActivity : BaseActivity() {
    private lateinit var binding: ActivityDevBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val skyLink = SkyLink()
        skyLink.inicializarGrafo()

        binding.devButtonBack.setOnClickListener{ onBackPressed() }
        binding.devButtonAccept.setOnClickListener{
            binding.devDesc.visibility = View.VISIBLE
            if (binding.devCmd.text.isNotEmpty()) {
                when (binding.devCmd.text.toString()) {
                    "show set" -> {
                        skyLink.verificarSetLineas()
                        binding.devDesc.text = getString(R.string.dev_set)
                    }
                    "show graph" -> {
                        skyLink.mostrarGrafo()
                        binding.devDesc.text = getString(R.string.dev_graph)
                    }
                    else -> {
                        binding.devDesc.text = getString(R.string.dev_wrongcmd)
                    }
                }
            } else {
                binding.devDesc.text = getString(R.string.dev_wrongcmd)
            }
        }
    }
}