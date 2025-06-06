package com.example.skylink.viewmodel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.R
import com.example.skylink.databinding.ItemDialogTerminalBinding
import com.example.skylink.model.singletons.CompanionObjects.Companion.COLOR_GETTER

//Adapter para el Dialog descriptivo de las estaciones de un recorrido
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
            val preDesc = getString(context!!, R.string.dialog_terminal_pre_desc)
            binding.itemDialogDesc.text = "$preDesc $data"
        }
    }

    fun addDataToList(list: List<String>) {
        datos.clear()
        datos.addAll(list)
    }
}