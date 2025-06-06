package com.example.skylink.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.viewmodel.clickListeners.OnStationClickListener
import com.example.skylink.model.dataClasses.Estacion
import com.example.skylink.databinding.ItemTerminalBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER

//Clase que sirve para adaptar el uso de los recycler views que muestran una lista de estaciones
class EstacionesAdapter(private val listener: OnStationClickListener): RecyclerView.Adapter<EstacionesAdapter.EstacionViewHolder>() {
    private val datos = mutableListOf<Estacion>()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstacionViewHolder {
        context = parent.context
        return EstacionViewHolder(
            ItemTerminalBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = datos.size

    override fun onBindViewHolder(holder: EstacionViewHolder, position: Int) {
        holder.binding(datos[position])
    }

    inner class EstacionViewHolder(private val binding: ItemTerminalBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Estacion){
            binding.itemTerminalDesc.text = data.nombre
            binding.itemTerminalImage.setColorFilter(ContextCompat.getColor(context!!, COLOR_GETTER.getColorID(data.lineas[0])))
            //Listener
            binding.root.setOnClickListener {
                listener.onItemClick(data.inputID)
            }
        }
    }

    fun addDataToList(list: List<Estacion>) {
        datos.clear()
        datos.addAll(list)
    }
}