package com.example.skylink

import android.os.Bundle
import android.view.View
import com.example.skylink.databinding.ActivityDevBinding
import com.example.skylink.singletons.CompanionObjects.Companion.SKYLINK_SINGLETON

class DevActivity : BaseActivity() {
    private lateinit var binding: ActivityDevBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val skyLink = SKYLINK_SINGLETON.getInstance(this)
        skyLink.inicializarGrafo()

        binding.devButtonBack.setOnClickListener{ onBackPressed() }
        binding.devButtonAccept.setOnClickListener{
            binding.devDesc.visibility = View.VISIBLE
            binding.devA.visibility = View.GONE
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
                    "17 04" -> {
                        binding.devDesc.visibility = View.GONE
                        binding.devA.visibility = View.VISIBLE
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