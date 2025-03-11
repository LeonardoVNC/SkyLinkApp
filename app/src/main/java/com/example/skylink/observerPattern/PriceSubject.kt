package com.example.skylink.observerPattern

interface PriceSubject {
    fun registerObserver(observer: PriceObserver)
    fun removeObserver(observer: PriceObserver)
    fun notifyObservers()
}