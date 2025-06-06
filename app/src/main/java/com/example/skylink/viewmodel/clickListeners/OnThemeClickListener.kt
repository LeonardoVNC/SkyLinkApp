package com.example.skylink.viewmodel.clickListeners

import com.example.skylink.model.dataClasses.Tema

//Interfaz usada para manejar el click de items en los recycler views de los temas
interface OnThemeClickListener {
    fun onItemClick(theme: Tema)
}