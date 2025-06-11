package com.example.skylink.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.databinding.ItemConfigOptionBinding
import com.example.skylink.model.dataClasses.ConfigOption
import com.example.skylink.viewmodel.clickListeners.OnConfigClickListener

class ConfigurationAdapter(private val listener: OnConfigClickListener, private val userType: Int ): RecyclerView.Adapter<ConfigurationAdapter.ConfigurationsViewHolder>() {
    private val datos = mutableListOf<ConfigOption>()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigurationsViewHolder {
        context = parent.context
        return ConfigurationsViewHolder(
            ItemConfigOptionBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = datos.size

    override fun onBindViewHolder(holder: ConfigurationsViewHolder, position: Int) {
        holder.binding(datos[position])
    }

    inner class ConfigurationsViewHolder(private val binding: ItemConfigOptionBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(data: ConfigOption) {
            if (userType == 0 && data.userRole == 1) {
                binding.root.visibility = View.GONE
            }
            binding.configOptionTitle.text = data.title

            binding.root.setOnClickListener{
                listener.onConfigClick(data.intent)
            }
            binding.configButtonPrice.setOnClickListener{
                listener.onConfigClick(data.intent)
            }
        }
    }
    //Puedo dwejar todas visitble spor defecto, pero si recibo un 0, oculto los 1

    fun addDataToList(list: List<ConfigOption>) {
        datos.clear()
        datos.addAll(list)
    }
}