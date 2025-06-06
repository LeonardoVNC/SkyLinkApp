package com.example.skylink.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.databinding.ItemThemeBinding
import com.example.skylink.model.dataClasses.Tema
import com.example.skylink.viewmodel.clickListeners.OnThemeClickListener

class TemasAdapter(private val listener: OnThemeClickListener): RecyclerView.Adapter<TemasAdapter.TemasViewHolder>() {
    private val datos = mutableListOf<Tema>()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemasViewHolder {
        context = parent.context
        return TemasViewHolder(
            ItemThemeBinding.inflate(LayoutInflater.from(context), parent,false)
        )
    }

    override fun getItemCount(): Int = datos.size;

    override fun onBindViewHolder(holder: TemasViewHolder, position: Int) {
        holder.binding(datos[position])
    }

    inner class TemasViewHolder(private val binding: ItemThemeBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Tema) {
            binding.themeTitle.text = data.nombre
            binding.themeColorPrimary.setBackgroundColor(data.colorPrimarioID)
            binding.themeColorSecondary.setBackgroundColor(data.colorSecundarioID)
            binding.themeColorAccent.setBackgroundColor(data.colorAccentID)
            binding.themeItem.setBackgroundColor(data.colorSurface)

            //Listener
            binding.root.setOnClickListener {
                listener.onItemClick(data)
            }
        }
    }

    fun addDataToList(temas: List<Tema>) {
        datos.clear()
        datos.addAll(temas)
    }
}