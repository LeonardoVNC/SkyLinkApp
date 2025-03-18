package com.example.skylink.viewmodel.observerPattern

interface PriceSubject {
    fun registerObserver(observer: PriceObserver)
    fun removeObserver(observer: PriceObserver)
    fun notifyObservers()
}