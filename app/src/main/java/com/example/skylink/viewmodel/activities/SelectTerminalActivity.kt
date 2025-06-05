package com.example.skylink.viewmodel.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skylink.R
import com.example.skylink.databinding.ActivitySelectTerminalBinding
import com.example.skylink.databinding.DialogSelectTerminalBinding
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_BEGIN
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_INPUT_END
import com.example.skylink.model.singletons.CompanionObjects.Companion.ID_LLAMADA_SKYLINK
import com.example.skylink.model.singletons.CompanionObjects.Companion.STATIONS_MAKER
import com.example.skylink.viewmodel.adapters.EstacionesAdapter
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener

//Activity para seleccionar las estaciones de origen y objetivo
class SelectTerminalActivity : BaseActivity(), OnStationClickListener {
    private lateinit var binding: ActivitySelectTerminalBinding
    private var inputBegin: Int = -1
    private var inputEnd: Int = -1
    private var selectBegin: Boolean = true
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectTerminalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Configuración de botones y clicks
        binding.terminalButtonBack.setOnClickListener{onBackPressed()}
        binding.selectTerminalOriginSelectButton.setOnClickListener{
            selectBegin = true
            openTerminalDialog()
        }
        binding.selectTerminalEndSelectButton.setOnClickListener{
            selectBegin = false
            openTerminalDialog()
        }

        binding.selectTerminalButtonNext.setOnClickListener{
            if (inputBegin == -1 || inputEnd == -1) {
                Toast.makeText(this, "Seleccione estaciones válidas por favor", Toast.LENGTH_SHORT).show()
            } else if (inputBegin == inputEnd) {
                Toast.makeText(this, "Ya se encuentra en su destino", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(ID_INPUT_BEGIN, inputBegin)
                intent.putExtra(ID_INPUT_END, inputEnd)
                intent.putExtra(ID_LLAMADA_SKYLINK, "Optimizar")
                startActivity(intent)
            }
        }
    }

    fun openTerminalDialog () {
        dialog = Dialog(this, R.style.DialogSinFondo)
        val binding = DialogSelectTerminalBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        //RecyclerView
        val estacionesAdapter = EstacionesAdapter(this)
        binding.dialogSelectTerminalRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = estacionesAdapter
        }

        val listaDeDatos = mutableListOf<Estacion>()
        listaDeDatos.addAll(STATIONS_MAKER.loadTerminalList(this))
        listaDeDatos.sortWith(compareBy { lineaConPrioridad[COLOR_GETTER.getColorID(it.lineas[0])] ?: Int.MAX_VALUE })
        estacionesAdapter.addDataToList(listaDeDatos)

        binding.dialogSelectTerminalButtonBack.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

    //Valor usado para ordenar las estaciones por color
    val lineaConPrioridad = mapOf(
        R.color.roja to 1,
        R.color.amarilla to 2,
        R.color.verde to 3,
        R.color.celeste to 4,
        R.color.blanca to 5,
        R.color.morada to 6,
        R.color.naranja to 7,
        R.color.azul to 8,
        R.color.cafe to 9,
        R.color.plateada to 10
    )

    override fun onItemClick(input: Int) {
        //Se comprueba en que casilla se guarda el nodo seleccionado
        if (selectBegin) inputBegin = input else inputEnd = input
        updateView(input)
        dialog.dismiss()
    }

    //Función empleada para actualizar los datos mostrados en la vista
    fun updateView(terminalID: Int) {
        val terminal = STATIONS_MAKER.loadTerminalList(this)[terminalID]
        val nombre = terminal.nombre
        val color = ContextCompat.getColor(this, COLOR_GETTER.getColorID(terminal.lineas[0]))
        if (selectBegin) {
            binding.selectTerminalOriginName.text = nombre
            binding.selectTerminalOriginImage.setColorFilter(color)
        } else {
            binding.selectTerminalEndName.text = nombre
            binding.selectTerminalEndImage.setColorFilter(color)
        }
    }
}