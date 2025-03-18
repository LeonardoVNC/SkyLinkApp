package com.example.skylink.model;

import com.example.skylink.model.customDataStructures.Dupla_IntArrDouble;

public interface Optimizador {
    Dupla_IntArrDouble optimizarRuta(int nodoInicial, int nodoObjetivo);
}
