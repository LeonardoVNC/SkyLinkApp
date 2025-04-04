package com.example.skylink.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.databinding.ItemDialogTerminalBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER

class DialogTerminalAdapter: RecyclerView.Adapter<DialogTerminalAdapter.DialogTerminalViewHolder>() {
    private val datos = mutableListOf<String>()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogTerminalViewHolder {
        context = parent.context
        return DialogTerminalViewHolder(
            ItemDialogTerminalBinding.inflate((LayoutInflater.from(context)), parent, false)
        )
    }

    override fun getItemCount(): Int = datos.size

    override fun onBindViewHolder(holder: DialogTerminalViewHolder, position: Int) {
        holder.binding((datos[position]))
    }

    inner class DialogTerminalViewHolder(private val binding: ItemDialogTerminalBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(data: String) {
            binding.itemDialogImage.setColorFilter(ContextCompat.getColor(context!!, COLOR_GETTER.getColorID(data)))
            binding.itemDialogDesc.text = data
        }
    }

    fun addDataToList(list: List<String>) {
        datos.clear()
        datos.addAll(list)
    }
}