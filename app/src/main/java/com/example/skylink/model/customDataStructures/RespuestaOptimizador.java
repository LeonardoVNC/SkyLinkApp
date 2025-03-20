package com.example.skylink.model.customDataStructures;

public class RespuestaOptimizador {
    private int tiempo;
    private int[] recorrido;
    private double precio;

    public RespuestaOptimizador(int tiempo, int[] recorrido, double precio) {
        this.tiempo = tiempo;
        this.recorrido = recorrido;
        this.precio = precio;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int[] getRecorrido() {
        return recorrido;
    }

    public double getPrecio() {
        return precio;
    }
}
