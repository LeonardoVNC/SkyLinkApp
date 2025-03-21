package com.example.skylink.model;

import android.content.Context;
import com.example.skylink.model.customDataStructures.RespuestaOptimizador;
import com.example.skylink.model.dataReader.LectorAssets;
import java.util.*;

//Clase encargada de la optimización de rutas, calcular el precio del recorrido y devolver el tiempo
public class SkyLink implements Optimizador {
    //Parámetros para la inicialización de la clase
    private LinkedList<int[]>[] grafo;
    private Set<Integer>[] lineas;
    private Map<Integer, String> nombreEstaciones;
    private int nodos;
    private int numLineas;
    private double pAbordaje;
    private double pTransbordo;

    //Parámetros para la lógica del algoritmo Dijkstra
    private final int INF = Integer.MAX_VALUE;
    private int[] dist;             //Arreglo que guardará la distancia entre el nodo de inicio y el resto de nodos
    private int[] padreDijkstra;    //Arreglo que guardará al nodo del que viene el camino con menor peso

    public SkyLink(String tipoCliente, Context context) {
        inicializarGrafo(context);
        inicializarNombreEstaciones();
        setTipoCliente(tipoCliente, context);
    }

    //Método encargado de estructurar el grafo, es no dirigido y tiene ponderación
    public void inicializarGrafo(Context context) {
        LectorAssets reader = new LectorAssets();
        List<List<Integer>> estructuraGrafo = reader.loadGraph(context);
        nodos = estructuraGrafo.get(0).get(0);
        numLineas = estructuraGrafo.get(0).get(1);

        grafo = new LinkedList[nodos];
        lineas = new HashSet[nodos];

        //Instanciamos una estructura de datos en cada posición del arreglo
        for (int i = 0; i < nodos; i++) {
            lineas[i] = new HashSet<>();
            grafo[i] = new LinkedList<int[]>();
        }

        int i = 1;
        while (estructuraGrafo.get(i).size() == 2) {
            int estacion = estructuraGrafo.get(i).get(0);
            int linea = estructuraGrafo.get(i).get(1);
            lineas[estacion].add(linea);
            i++;
        }
        while (i < estructuraGrafo.size() && estructuraGrafo.get(i).size() == 3) {
            int estacionA = estructuraGrafo.get(i).get(0);
            int estacionB = estructuraGrafo.get(i).get(1);
            int tiempo = estructuraGrafo.get(i).get(2);
            grafo[estacionA].add(new int[]{estacionB, tiempo});
            grafo[estacionB].add(new int[]{estacionA, tiempo});
            System.out.println(i);
            i++;
        }
    }

    //Método encargado de asignar un nombre a cada uno de los nodos o estaciones
    public void inicializarNombreEstaciones() {
        nombreEstaciones = new HashMap<>();
        nombreEstaciones.put(0, "Río Seco");
        nombreEstaciones.put(1, "UPEA");
        nombreEstaciones.put(2, "Plaza La Paz");
        nombreEstaciones.put(3, "Plaza La Libertad");
        nombreEstaciones.put(4, "16 de Julio");
        nombreEstaciones.put(5, "Cementerio");
        nombreEstaciones.put(6, "Central");
        nombreEstaciones.put(7, "Armentia");
        nombreEstaciones.put(8, "Periférica");
        nombreEstaciones.put(9, "Villarroel");
        nombreEstaciones.put(10, "Busch");
        nombreEstaciones.put(11, "Triangular");
        nombreEstaciones.put(12, "Del Poeta");
        nombreEstaciones.put(13, "Las Villas");
        nombreEstaciones.put(14, "Prado");
        nombreEstaciones.put(15, "Teatro al Aire Libre");
        nombreEstaciones.put(16, "Libertador");
        nombreEstaciones.put(17, "Alto Obrajes");
        nombreEstaciones.put(18, "Obrajes");
        nombreEstaciones.put(19, "Irpavi");
        nombreEstaciones.put(20, "Sopocachi");
        nombreEstaciones.put(21, "Buenos Aires");
        nombreEstaciones.put(22, "Mirador");
        nombreEstaciones.put(23, "6 de Marzo");
        nombreEstaciones.put(24, "Faro Murillo");
        nombreEstaciones.put(25, "Obelisco");
    }

    //Método que devuelve el valor asignado del mapD nombreEstaciones
    public String estNomb(int key) {
        if (!nombreEstaciones.containsKey(key)) {
            System.out.println("Error en estNomb, verica la key y el estado del map nombreEstaciones.");
            return "";
        }
        return nombreEstaciones.get(key);
    }

    //Método encargado de definir el precio de abordo y transbordo según el tipo de cliente
    public void setTipoCliente(String tipoCliente, Context context) {
        LectorAssets reader = new LectorAssets();
        String[] lineaPrecio = reader.searchTitlePrices(context, tipoCliente);
        pAbordaje = Double.parseDouble(lineaPrecio[2]);
        pTransbordo = Double.parseDouble(lineaPrecio[3]);
    }

    @Override
    public RespuestaOptimizador optimizarRuta(int estacionOrigen, int estacionObjetivo) {
        if (estacionOrigen == estacionObjetivo) {
            System.out.println("Ruta a la misma estación, no hay costo ni ruta.");
            return new RespuestaOptimizador(0, new int[]{}, 0.0);
        }
        dist = new int[grafo.length];
        padreDijkstra = new int[grafo.length];
        Arrays.fill(dist, INF);     //Se llena el arreglo de distancias con infinito para luego compararlo
        Comparator<int[]> comparator = (a, b) -> a[0] - b[0];           //Se instancia la cola de prioridad
        PriorityQueue<int[]> pq = new PriorityQueue<>(comparator);      //Por defecto da prioridad al número más bajo
        dist[estacionOrigen] = 0;                          //La distancia desde el nodo inicial es 0
        padreDijkstra[estacionOrigen] = -1;                //El nodo inicial no tiene un nodo padre
        pq.add(new int[]{0, estacionOrigen});           //Se añade a la cola un arreglo con la distancia en 0 y el nodo inicial
        while (!pq.isEmpty()) {                         //Se sigue el algoritmo de Dijkstra mientras la cola no este vacía
            int pesoActual = pq.peek()[0];              //Se guarda el peso y el nodo relacionado
            int nodoActual = pq.peek()[1];              //del frente de la cola de prioridad
            pq.poll();                                  //una vez guardada la información se descarta al arreglo
            if (pesoActual > dist[nodoActual])          //Si el peso del frente es mayor a la distancia del nodo del frente
                continue;                               //se corta este caso del while y se sigue con el siguiente
            for (int i = 0; i < grafo[nodoActual].size(); i++) {        //Evaluamos a los vecinos del nodo del frente
                int siguienteNodo = grafo[nodoActual].get(i)[0];
                int peso = grafo[nodoActual].get(i)[1];
                int siguientePeso = pesoActual + peso;                  //Se evalua el peso que tomará este camino
                if (siguientePeso < dist[siguienteNodo]) {              //Si el nuevo camino resulta ser menor, se efectua el Relax
                    dist[siguienteNodo] = siguientePeso;                //la distancia y el padre se sobreescriben
                    padreDijkstra[siguienteNodo] = nodoActual;
                    pq.add(new int[]{siguientePeso, siguienteNodo}); //Se agregan los caminos posibles desde el nuevo camino
                }
            }
        }
        //Si nunca visitamos al nodo objetivo, INF no habra sido sobreescrito
        if (dist[estacionObjetivo] == INF) {
            System.out.println("No se puede llegar desde " + estNomb(estacionOrigen) + " hasta " + estNomb(estacionObjetivo));
            return new RespuestaOptimizador(-1, new int[]{-1}, -1.0);
        } else {
            int[] nodosRecorrido = new int[grafo.length];
            Arrays.fill(nodosRecorrido, -1);
            Set<Integer> lineasRecorridas = new HashSet<>();
            double costoRecorrido = 0.0;

            nodosRecorrido[0] = estacionObjetivo;

            int i = estacionObjetivo;
            int j = 1;
            //Se recorre el camino desde el nodo objetivo hasta el nodo inicial (de padre -1)
            while (padreDijkstra[i] != -1) {
                lineasRecorridas.add(mismaLinea(i, padreDijkstra[i]));
                nodosRecorrido[j] = padreDijkstra[i];
                j++;
                i = padreDijkstra[i];
            }
            costoRecorrido = (lineasRecorridas.size() - 1) * pTransbordo + pAbordaje;
            return new RespuestaOptimizador(dist[estacionObjetivo], nodosRecorrido, costoRecorrido);
        }
    }

    //Método que verifica si 2 nodos pertenecen a una misma línea
    public int mismaLinea(int nodoA, int nodoB) {
        for (int i = 0; i < numLineas; i++) {
            if (lineas[nodoA].contains(i) && lineas[nodoB].contains(i)) {
                return i;
            }
        }
        return -1;
    }

    // TEST - Métodos empleados para verificación de estructuras de datos durante el desarrollo
    //Método que muestra cada nodo con sus respectivas líneas
    public void verificarSetLineas() {
        for (int i = 0; i < grafo.length; i++) {
            Integer[] arr = lineas[i].toArray(new Integer[0]); //Se convierten los sets en arreglos
            System.out.print("El nodo " + i + " pertenece a las líneas: ");
            mostrarArreglo(arr);    //y se muestran con ayuda de mostrarArreglo
        }
    }

    //Método para mostrar un grafo ponderado
    public void mostrarGrafo() {
        for (int i = 0; i < grafo.length; i++) {
            System.out.print("Nodo " + i + ": ");
            for (int j = 0; j < grafo[i].size(); j++) {
                mostrarArreglo(grafo[i].get(j));
            }
            System.out.println();
        }
    }

    //Conjunto de métodos para mostrar arreglos, solo varían en el tipo de dato
    public static void mostrarArreglo(Integer[] arr) {
        System.out.print("[" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            System.out.print("," + arr[i]);
        }
        System.out.println("]");
    }

    public static void mostrarArreglo(int[] arr) {
        System.out.print("[" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            System.out.print("," + arr[i]);
        }
        System.out.print("]");
    }
}
