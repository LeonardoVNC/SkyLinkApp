package com.example.skylink.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skylink.OnPriceClickListener
import com.example.skylink.R
import com.example.skylink.dataClasses.Precios
import com.example.skylink.databinding.ItemSetPricesBinding

class PreciosAdapter(private val listener: OnPriceClickListener): RecyclerView.Adapter<PreciosAdapter.PreciosViewHolder>() {
    private val datos = mutableListOf<Precios>()
    private var context: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreciosViewHolder {
        context = parent.context
        return PreciosViewHolder(
            ItemSetPricesBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = datos.size

    override fun onBindViewHolder(holder: PreciosViewHolder, position: Int) {
        holder.binding(datos[position])
    }

    inner class PreciosViewHolder(private val binding: ItemSetPricesBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Precios) {
            binding.itemSetPricesTitle.text = data.titulo
            binding.itemSetPricesDesc.text = data.descripcion
            val stringBoard = context?.getString(R.string.item_price_board)
            val stringTransboard = context?.getString(R.string.item_price_transboard)
            val currencyString = context?.getString(R.string.result_price)
            binding.itemSetPricesOnboard.text = "$stringBoard ${String.format("%.2f",data.precioAbordaje)}$currencyString"
            binding.itemSetPricesTransboard.text = "$stringTransboard ${String.format("%.2f",data.precioTransbordo)}$currencyString"
            //Listener
            binding.root.setOnClickListener {
                listener.onItemClick(data.titulo)
            }
        }
    }

    fun addDataToList(list: List<Precios>) {
        datos.clear()
        datos.addAll(list)
    }
}