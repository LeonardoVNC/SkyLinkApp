package com.example.skylink;

import java.util.*;

public class SkyLink {
    static LinkedList<int[]>[] grafo;
    static Set<Integer>[] lineas;
    static int numLineas;

    static int pAbordaje;
    static int pTransbordo;

    //Método encargado de definir el precio de abordo y transbordo según el tipo
    //Los precios se cuentan en centavos
    public static void setTipoCliente(int type) {
        if (type == 1) {
            pAbordaje = 300;
            pTransbordo = 200;
        } else if (type == 2 || type == 3) {
            pAbordaje = 150;
            pTransbordo = 100;
        }
    }

    //Método que devuelve el nombre de la estación a la que pertenece el nodo n
    public static String estNomb(int n) {
        switch (n) {
            case 0:
                return "Río Seco";
            case 1:
                return "UPEA";
            case 2:
                return "Plaza La Paz";
            case 3:
                return "Plaza La Libertad";
            case 4:
                return "16 de Julio";
            case 5:
                return "Cementerio";
            case 6:
                return "Central";
            case 7:
                return "Armentia";
            case 8:
                return "Periférico";
            case 9:
                return "Villarroel";
            case 10:
                return "Busch";
            case 11:
                return "Triangular";
            case 12:
                return "Del Poeta";
            case 13:
                return "Las Villas";
            case 14:
                return "Prado";
            case 15:
                return "Teatro al Aire Libre";
            case 16:
                return "Libertador";
            case 17:
                return "Alto Obrajes";
            case 18:
                return "Obrajes";
            case 19:
                return "Irpavi";
            case 20:
                return "Sopocachi";
            case 21:
                return "Buenos Aires";
            case 22:
                return "Mirador";
            case 23:
                return "6 de Marzo";
            case 24:
                return "Faro Murillo";
            case 25:
                return "Obelisco";
            default:
                return "Error en el id de la estación, verifique el método nombEstacion en la Clase SkyLink";
        }
    }

    //Método encargado de estructurar todo el grafo, es no dirigido y tiene ponderación
    public void inicializarGrafo() {
        int nodos = 26;
        grafo = new LinkedList[nodos];
        lineas = new HashSet[nodos];
        for (int i = 0; i < nodos; i++) { //Instanciamos una estructura de datos en cada posición del arreglo
            lineas[i] = new HashSet<>();
            grafo[i] = new LinkedList<int[]>();
        }

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

    static final int INF = Integer.MAX_VALUE; //Definimos infinito como el número más alto en java (para ints)
    static int[] dist; //Arreglo que guardará la distancia entre el nodo de inicio y el resto de nodos
    static int[] padreDijkstra; //Arreglo que guardará al nodo del que viene el camino con menor peso

    public int[] optimizacionTiempo(int nodoInicial, int nodoObjetivo) {
        dist = new int[grafo.length];           //Instanciamos los 2 arreglos
        padreDijkstra = new int[grafo.length];  //Con el numero de nodos que hay
        Arrays.fill(dist, INF); //Se llena el arreglo de distancias con infinito para luego compararlo
        Comparator<int[]> comparator = (a, b) -> a[0] - b[0];       //Se instancia la cola de prioridad
        PriorityQueue<int[]> pq = new PriorityQueue<>(comparator);  //Por defecto da prioridad al número más bajo
        dist[nodoInicial] = 0; //La distancia desde el nodo inicial es 0
        padreDijkstra[nodoInicial] = -1; //El nodo inicial no tiene un nodo padre
        pq.add(new int[] { 0, nodoInicial }); //Se añade a la cola un arreglo con la distancia en 0 y el nodo inicial
        while (!pq.isEmpty()) { //Se sigue el algoritmo de Dijkstra mientras la cola no este vacía
            int pesoActual = pq.peek()[0];  //Se guarda el peso y el nodo relacionado
            int nodoActual = pq.peek()[1];  //del frente de la cola de prioridad
            pq.poll();                      //una vez guardada la información se descarta al arreglo
            if (pesoActual > dist[nodoActual])  //Si el peso del frente es mayor a la distancia del nodo del frente
                continue;                       //se corta este caso del while y se sigue con el siguiente
            for (int i = 0; i < grafo[nodoActual].size(); i++) { //Evaluamos a los vecinos del nodo del frente
                int siguienteNodo = grafo[nodoActual].get(i)[0];
                int peso = grafo[nodoActual].get(i)[1];
                int siguientePeso = pesoActual + peso; //Se evalua el peso que tomará este camino
                if (siguientePeso < dist[siguienteNodo]) {  //Si el nuevo camino resulta ser menor, se efectua el Relax
                    dist[siguienteNodo] = siguientePeso;    //la distancia y el padre se sobreescriben
                    padreDijkstra[siguienteNodo] = nodoActual;
                    pq.add(new int[] { siguientePeso, siguienteNodo }); //Se agregan los caminos posibles desde el nuevo camino
                }
            }
        }
        if (dist[nodoObjetivo] == INF) { //Si nunca visitamos al nodo objetivo, INF no habra sido sobreescrito
            System.out.println("No se puede llegar de " + estNomb(nodoInicial) + " a " + estNomb(nodoObjetivo));
            return new int[]{-1};
        } else {
            int[] respuesta = new int[grafo.length];
            Arrays.fill(respuesta,-1);

            respuesta[0] = dist[nodoObjetivo];
            respuesta[1] = nodoObjetivo;

            int i = nodoObjetivo;
            int j = 2;
            while (padreDijkstra[i] != -1) { //Se recorre el camino desde el nodo objetivo hasta el nodo inicial (de padre -1)
                respuesta[j] = padreDijkstra[i];
                j++;
                i = padreDijkstra[i];
            }
            return respuesta;
        }
    }

    static int[] nivel;     //Arreglo encargado de guardar a que nivel pertenece cada estación
    static boolean[] vis;   //Arreglo que se encarga de no re visitar nodos
    static int[] padre;     //Arreglo que guarda el nodo padre de cada nodo

    public static void optimizacionPrecio(int nodoInicial, int nodoObjetivo) {
        if (nodoInicial == nodoObjetivo) { //Dado el caso de viaje al mismo lugar, no se ejecuta el algoritmo
            System.out.println("Ya te encuentras en tu destino, el precio a pagar es 0 Bs");
        } else {
            Queue<Integer> kiwi = new LinkedList<>(); //Se crea una cola para el BFS
            nivel = new int[grafo.length];      //Se instancian los arreglos con el tamaño requerido
            vis = new boolean[grafo.length];
            kiwi.offer(nodoInicial);    //Se agrega el nodo inicial a la cola
            nivel[nodoInicial] = 1;     //Se define su nivel como 1
            padre = new int[grafo.length];
            boolean nodoEncontrado = false; //Boolean usado para terminar el BFS una vez se encuentre al nodo objetivo
            while (!kiwi.isEmpty() && !nodoEncontrado) {
                int nodoActual = kiwi.poll(); //Se guarda el frente de la cola
                if (nodoActual == nodoObjetivo) {   //En caso de haberse encontrado el nodo objetivo
                    nodoEncontrado = true;          //el boolean cambia a true para cortar el BFS
                    vis[nodoActual] = true;         //y marcamos el nodo objetivo como visitado
                    break;
                }
                if (!vis[nodoActual]) {     //Si aun no se visita al nodo actual
                    vis[nodoActual] = true; //Lo marcamos como visitado
                    for (int i = 0; i < grafo[nodoActual].size(); i++) { //Guardamos a todos sus vecinos
                        int vecino = grafo[nodoActual].get(i)[0];
                        if (!vis[vecino]) { //Se agregan a la cola en caso de no haberse visitado aun
                            kiwi.offer(vecino);
                            if (nivel[vecino] == 0) { //Si el nivel del nodo vecino aun no se ha definido
                                nivel[vecino] = nivel[nodoActual] + 1; //se define el nivel del nodo vecino
                                padre[vecino] = nodoActual;            //y se define al nodo padre
                            }
                        }
                    }
                }
            }
            if (!vis[nodoObjetivo]) { //Si no se llegó a visitar al nodo Objetivo...
                System.out.println("No se puede llegar de  " + estNomb(nodoInicial) + " a " + estNomb(nodoObjetivo));
            } else {
                Set<Integer> set = new HashSet<>(); //Se crea un set que guarda las líneas que comprenden el recorrido
                System.out.print("El camino comprende las estaciones " + estNomb(nodoObjetivo));
                int nodoActual = nodoObjetivo;
                for (int i = 1; i < nivel[nodoObjetivo]; i++) {         //Mientras se recorre el camino para ir imprimiendo el recorrido
                    set.add(mismaLinea(nodoActual, padre[nodoActual])); //se almacena en un set todas las líneas por las que se pasó
                    nodoActual = padre[nodoActual];
                    System.out.print(", " + estNomb(nodoActual));
                }
                int costo = 0;                          //Se crea una variable costo
                for (int i = 0; i < set.size(); i++) {  //que recorriendo todo el tamaño del set
                    if (i != 0) {                       //sumara el precio de transbordo a todas las líneas que no sean la primera
                        costo += pTransbordo;
                    } else {                            //la primera línea suma el precio de abordo
                        costo += pAbordaje;
                    }
                }
                //Se castea a double el costo y se divide entre 100 para obtener el precio en Bs y no en centavos
                System.out.println(". Con un precio total de " + ((double) (costo) / 100) + "Bs"); 
            }
        }
    }

    //Método que verifica si 2 nodos pertenecen a una misma línea
    public static int mismaLinea(int nodoA, int nodoB) {
        for (int i = 0; i < numLineas; i++) {
            if (lineas[nodoA].contains(i) && lineas[nodoB].contains(i)) {
                return i;
            }
        }
        return -1;
    }

    // TEST - Métodos empleados para verificación de estructuras de datos durante el desarrollo

    //Método que muestra cada nodo con sus respectivas líneas
    public static void verificarSetLineas() {
        for (int i = 0; i < grafo.length; i++) {
            Integer[] arr = lineas[i].toArray(new Integer[0]); //Se convierten los sets en arreglos
            System.out.print("El nodo " + i + " pertenece a las líneas: ");
            mostrarArreglo(arr);    //y se muestran con ayuda de mostrarArreglo
        }
    }

    //Método para mostrar un grafo ponderado
    public static void mostrarGrafo() {
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
