package com.example.skylink.model;

import com.example.skylink.model.customDataStructures.RespuestaOptimizador;

import java.util.List;

//Interfaz que debe implementar cualquier clase que busque optimizar el trayecto de
// una estación a otra dentro del Teleférico
public interface Optimizador {
    void inicializarGrafo (List<List<Integer>> datosGrafo);
    RespuestaOptimizador optimizarRuta(int estacionOrigen, int estacionObjetivo);
}
