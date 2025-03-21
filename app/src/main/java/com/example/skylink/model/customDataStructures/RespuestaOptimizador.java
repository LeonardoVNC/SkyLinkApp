package com.example.skylink.model.customDataStructures;

//Esta clase es usada para guardar toda la información proporcionada por la interfaz Optimizador en
// una única estructura de datos, facilitando su uso y entendimiento.
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
