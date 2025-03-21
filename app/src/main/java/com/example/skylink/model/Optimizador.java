package com.example.skylink.model;

import com.example.skylink.model.customDataStructures.RespuestaOptimizador;

//Interfaz que debe implementar cualquier clase que busque optimizar el trayecto de
// una estación a otra dentro del Teleférico
public interface Optimizador {
    RespuestaOptimizador optimizarRuta(int estacionOrigen, int estacionObjetivo);
}
