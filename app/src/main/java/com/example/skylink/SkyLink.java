package com.example.skylink;

import com.example.skylink.customDataStructures.Dupla_IntArrDouble;

import java.util.*;

public class SkyLink {
    //Parámetros para la inicialización de la clase
    private LinkedList<int[]>[] grafo;
    private Set<Integer>[] lineas;
    private Map<Integer, String> nombreEstaciones;
    private int nodos = 26;
    private int numLineas = 10;
    private double pAbordaje;
    private double pTransbordo;

    //Parámetros para la lógica del algoritmo Dijkstra
    private final int INF = Integer.MAX_VALUE;
    private int[] dist;             //Arreglo que guardará la distancia entre el nodo de inicio y el resto de nodos
    private int[] padreDijkstra;    //Arreglo que guardará al nodo del que viene el camino con menor peso

    public SkyLink (String tipoCliente) {
        grafo = new LinkedList[nodos];
        lineas = new HashSet[nodos];
        inicializarGrafo();
        inicializarNombreEstaciones();
        setTipoCliente(tipoCliente);
    }

    //Método encargado de estructurar todo el grafo, es no dirigido y tiene ponderación
    public void inicializarGrafo() {
        //Instanciamos una estructura de datos en cada posición del arreglo
        for (int i = 0; i < nodos; i++) {
            lineas[i] = new HashSet<>();
            grafo[i] = new LinkedList<int[]>();
        }

        //Se llenan los datos del grafo
        //Si grafo[a].add(new int[]{b, c}); => a=nodo origen, b=nodo destino, c=tiempo o ponderación
        grafo[0].add(new int[]{1, 7});

        grafo[1].add(new int[]{0, 7});
        grafo[1].add(new int[]{2, 5});

        grafo[2].add(new int[]{1, 5});
        grafo[2].add(new int[]{3, 4});

        grafo[3].add(new int[]{2, 4});
        grafo[3].add(new int[]{4, 4});

        grafo[4].add(new int[]{3, 4});
        grafo[4].add(new int[]{5, 5});
        grafo[4].add(new int[]{24, 8});

        grafo[5].add(new int[]{4, 5});
        grafo[5].add(new int[]{6, 5});

        grafo[6].add(new int[]{5, 5});
        grafo[6].add(new int[]{7, 4});

        grafo[7].add(new int[]{6, 4});
        grafo[7].add(new int[]{8, 4});

        grafo[8].add(new int[]{7, 4});
        grafo[8].add(new int[]{9, 4});

        grafo[9].add(new int[]{8, 4});
        grafo[9].add(new int[]{10, 4});

        grafo[10].add(new int[]{9, 4});
        grafo[10].add(new int[]{11, 5});
        grafo[10].add(new int[]{13, 4});

        grafo[11].add(new int[]{10, 5});
        grafo[11].add(new int[]{12, 3});

        grafo[12].add(new int[]{11, 3});
        grafo[12].add(new int[]{16, 3});
        grafo[12].add(new int[]{15, 3});

        grafo[13].add(new int[]{10, 4});

        grafo[14].add(new int[]{15, 3});

        grafo[15].add(new int[]{12, 3});
        grafo[15].add(new int[]{14, 4});

        grafo[16].add(new int[]{12, 3});
        grafo[16].add(new int[]{17, 3});
        grafo[16].add(new int[]{20, 6});

        grafo[17].add(new int[]{16, 3});
        grafo[17].add(new int[]{18, 5});

        grafo[18].add(new int[]{17, 5});
        grafo[18].add(new int[]{19, 8});

        grafo[19].add(new int[]{18, 8});

        grafo[20].add(new int[]{16, 6});
        grafo[20].add(new int[]{21, 6});

        grafo[21].add(new int[]{20, 6});
        grafo[21].add(new int[]{22, 4});

        grafo[22].add(new int[]{21, 4});
        grafo[22].add(new int[]{24, 4});

        grafo[23].add(new int[]{24, 8});

        grafo[24].add(new int[]{4,8});
        grafo[24].add(new int[]{22,4});
        grafo[24].add(new int[]{25,8});
        grafo[24].add(new int[]{23,8});

        grafo[25].add(new int[]{24,8});

        //Se seleccionan las líneas a las que pertencen los distintos nodos o estaciones
        lineas[0].add(0);

        lineas[1].add(0);

        lineas[2].add(0);

        lineas[3].add(0);

        lineas[4].add(0);
        lineas[4].add(1);
        lineas[4].add(8);

        lineas[5].add(1);

        lineas[6].add(1);
        lineas[6].add(2);

        lineas[7].add(2);

        lineas[8].add(2);

        lineas[9].add(2);
        lineas[9].add(3);

        lineas[10].add(3);
        lineas[10].add(4);

        lineas[11].add(3);

        lineas[12].add(3);
        lineas[12].add(5);

        lineas[13].add(4);

        lineas[14].add(5);

        lineas[15].add(5);

        lineas[16].add(5);
        lineas[16].add(6);
        lineas[16].add(7);

        lineas[17].add(6);

        lineas[18].add(6);

        lineas[19].add(6);

        lineas[20].add(7);

        lineas[21].add(7);

        lineas[22].add(7);
        lineas[22].add(8);

        lineas[23].add(9);

        lineas[24].add(8);
        lineas[24].add(9);

        lineas[25].add(9);
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

    //Método que devuelve el valor asignado del map nombreEstaciones
    public String estNomb(int key) {
        if (!nombreEstaciones.containsKey(key)) {
            System.out.println("Error en estNomb, verica la key y el estado del map nombreEstaciones.");
            return "";
        }
        return nombreEstaciones.get(key);
    }

    //Método encargado de definir el precio de abordo y transbordo según el tipo de cliente
    public void setTipoCliente(String tipoCliente) {
        switch (tipoCliente) {
            case "Estándar": {
                pAbordaje = 3.0;
                pTransbordo = 2.0;
                break;
            }
            case "Estudiante": {
                pAbordaje = 1.5;
                pTransbordo = 1.0;
                break;
            }
            case "Adulto mayor": {
                pAbordaje = 1.5;
                pTransbordo = 1;
                break;
            }
            default: {
                System.out.println("Parámetro inválido en setTipoCliente");
                break;
            }
        }
    }

    public Dupla_IntArrDouble optimizarRuta(int nodoInicial, int nodoObjetivo) {
        if (nodoInicial == nodoObjetivo) {
            System.out.println("Ruta a la misma estación, no hay costo ni ruta.");
            return new Dupla_IntArrDouble(new int[]{nodoInicial}, 0);
        }
        dist = new int[grafo.length];
        padreDijkstra = new int[grafo.length];
        Arrays.fill(dist, INF);     //Se llena el arreglo de distancias con infinito para luego compararlo
        Comparator<int[]> comparator = (a, b) -> a[0] - b[0];           //Se instancia la cola de prioridad
        PriorityQueue<int[]> pq = new PriorityQueue<>(comparator);      //Por defecto da prioridad al número más bajo
        dist[nodoInicial] = 0;                          //La distancia desde el nodo inicial es 0
        padreDijkstra[nodoInicial] = -1;                //El nodo inicial no tiene un nodo padre
        pq.add(new int[] { 0, nodoInicial });           //Se añade a la cola un arreglo con la distancia en 0 y el nodo inicial
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
                    pq.add(new int[] { siguientePeso, siguienteNodo }); //Se agregan los caminos posibles desde el nuevo camino
                }
            }
        }
        //Si nunca visitamos al nodo objetivo, INF no habra sido sobreescrito
        if (dist[nodoObjetivo] == INF) {
            System.out.println("No se puede llegar desde " + estNomb(nodoInicial) + " hasta " + estNomb(nodoObjetivo));
            return new Dupla_IntArrDouble(new int[]{-1}, -1.0);
        } else {
            int[] nodosRecorrido = new int[grafo.length];
            Arrays.fill(nodosRecorrido,-1);
            Set<Integer> lineasRecorridas = new HashSet<>();
            double costoRecorrido = 0.0;

            nodosRecorrido[0] = dist[nodoObjetivo];
            nodosRecorrido[1] = nodoObjetivo;

            int i = nodoObjetivo;
            int j = 2;
            //Se recorre el camino desde el nodo objetivo hasta el nodo inicial (de padre -1)
            while (padreDijkstra[i] != -1) {
                lineasRecorridas.add(mismaLinea(i,padreDijkstra[i]));
                nodosRecorrido[j] = padreDijkstra[i];
                j++;
                i = padreDijkstra[i];
            }
            costoRecorrido = (lineasRecorridas.size()-1)*pTransbordo+pAbordaje;
            return new Dupla_IntArrDouble(nodosRecorrido, costoRecorrido);
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

    public static void mostrarArreglo(boolean[] arr) {
        System.out.print("[" + arr[0]);
        for (int i = 1; i < arr.length; i++) {
            System.out.print("," + arr[i]);
        }
        System.out.print("]");
    }
}
