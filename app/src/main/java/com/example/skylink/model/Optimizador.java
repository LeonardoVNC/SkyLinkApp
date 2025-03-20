package com.example.skylink.model;

import com.example.skylink.model.customDataStructures.RespuestaOptimizador;

public interface Optimizador {
    RespuestaOptimizador optimizarRuta(int nodoInicial, int nodoObjetivo);
}
