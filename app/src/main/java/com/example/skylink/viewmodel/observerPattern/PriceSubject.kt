package com.example.skylink.viewmodel.observerPattern

//Interfaz para el Sujeto a observar del patrón Observer
interface PriceSubject {
    fun registerObserver(observer: PriceObserver)
    fun removeObserver(observer: PriceObserver)
    fun notifyObservers()
}