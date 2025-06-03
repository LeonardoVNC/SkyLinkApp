# SkyLink
EL tráfico en la ciudad de La Paz puede llegar a congestionarse demasiado y puede resultar confuso para ciertos trayectos, por lo que hay ocasiones en las que resulta más rápido y económico utilizar el Teleférico para movilizarse entre las distintas zonas de la ciudad e incluso por la ciudad de El Alto. Sin embargo, para nuevos usuarios puede resultar complicado determinar que estaciones deben cruzar para llegar a su destino, el precio también puede resultar confuso, sin mencionar que el tiempo requerido para llegar de un punto a otro resulta incierto para cualquiera que no realice dicho trayecto con regularidad. SkyLink se presenta como una solución para estas problemáticas, haciendo el uso del Teleférico mucho más sencillo e intuitivo. Este proyecto se centra en celulares Android.

## Lógica del proyecto
La estructura del teleférico puede asemejarse a un grafo ponderado bi-direccional, donde cada estación representa un nodo y cada trayecto entre las estaciones representa una arista, representandose el tiempo necesario para llegar de una estación a otra como el peso de dicha arista.

Bajo la idea de representar el teleférico como un grafo ponderado, es posible emplear el algoritmo de Dijkstra (usado para encontrar el camino más corto o liviano entre 2 nodos dentro de un grafo ponderado) para encontrar la solución al problema principal, obtener el recorrido más rápido para llegar de una estación a otra.

## Datos usados
Actualmente, dentro del proyecto se emplea la siguiente distribución de estaciones:
![image](https://github.com/user-attachments/assets/f7c559a7-420d-4b23-b785-abf670793592)
La forma de ingresar estos y otros datos se encuentra documentada dentro del archivo [AssetsFormat.md](/AssetsFormat.md)

