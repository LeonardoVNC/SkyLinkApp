# Formato del guardado de datos en Assets
Para facilitar la edición de los datos que emplea la aplicación para funcionar correctamente se utilizan Assets. 
Desde este tipo de archivos se carga información que la aplicación puede leer, interpretar y utilizar para la creación de estructuras de datos. 
Los Assets deben seguir una estructura ordenada determinada por la información que guardan. A continuación se describe como se estructuran los distintos Assets presentes en la Aplicación:
## Grafo del Teleférico
Este archivo de texto ubicado en [grafoTeleferico.txt](app/src/main/assets/grafoTeleferico.txt) es probablemente el recurso más importante para la aplicación, ya que se encarga de guardar toda la información necesaria para estructurar el grafo que representa la distribución del Teleférico. 
Guarda información importante como los identificadores de cada estación, las líneas a las que pertenece cada estación y las distintas conexiones que existen entre las estaciones con su respectivo tiempo. 

Antes de ver la estructura del archivo, es necesario ver los IDs con los que se identificarán a las distintas estaciones y colores:
- **IDs para Nodos:**
    Son los encargados de identificar bajo un número único a las distintas estaciones que hay en el grafo. Ninguno puede ser un número mayor o igual al número de nodos en el grafo, por lo que la mejor manera de enumerarlos es iniciando en 0 y terminando en el número total de nodos en el grafo menos 1. No existe una regla para identificar cada estación, pero se debe tener en cuenta que los IDs escogidos se utilizarán ampliamente a lo largo de la ejecución de la aplicación, por lo que resulta útil seguir cierto orden o recordar a que estación pertenece cada ID.
- **IDs para Líneas**
    Se encargan de identificar a las distintas líneas existentes en la distribución del teleférico, como ser la línea roja, la línea amarilla, etc. Al igual que con los nodos, estos IDs deben enumerarse desde 0 para no soprepasar el número de líneas en el programa.


Este archivo se divide en 3 secciones principales:
- **Estructura general del Grafo:**
    Se trata de una única línea que contiene un par de números enteros, estos corresponden a el número de estaciones o nodos presentes en el grafo y al número de líneas a representar. Ambos valores se encuentran separados por un espacio " ". La razón de estos valores se encuentra en limitar el tamaño de las estructuras de datos que se emplearán, esto con el fin de optimizar el espacio utilizado, además, limita el rango de los IDs tanto de nodos como de líneas para que al ejecutarse el algoritmo se pueda comprobar cada estructura iterativamente, evitando saltos innecesarios o inicios poco usuales. La forma de escribir esta línea es la siguiente:
```
numeroNodos numeroLineas
```

- **Asignación de Lineas:**
    Es un conjunto de líneas, cada una contiene un par de números enteros que se corresponden al identificador de una estación y al identificador de la línea a la que pertenece. La forma de escribir líneas que encajen en este conjunto es la siguiente:
```
idNodo idLinea
```
    
* Para las estaciones que pertenecen a más de una línea es necesario repetir el ID de la estación con el nuevo ID de la línea como se muestra a continuación:

```
idNodoA idLinea1
idNodoA idLinea2
idNodoB idLinea2
```

- **Asignación de Aristas:**
    Es un conjunto de líneas, cada una contiene 3 números enteros que corresponden al ID del primer nodo, el ID del segundo nodo y el tiempo (en segundos) que toma ir entre esos 2 nodos. Se debe tener especial cuidado con esta sección del asset, ya que líneas incorrectas pueden generar conexiones no deseadas, estaciones mal conectadas o hasta nodos aislados del grafo. Cualquier problema relacionado a la conexión del grafo puede estar relacionado a este archivo. La forma de escribir líneas para este conjunto es la siguiente:
```
idNodoA idNodoB tiempo
```

## Información Estaciones
Este asset ubicado en [estaciones.txt](/app/src/main/assets/estaciones.txt) se encarga de cargar información empleada por la aplicación para mostrar los datos de una manera más visual. Asigna nombres a los nodos y también carga la información de las líneas a las que pertence cada estación. 

Para guardar información en este archivo se debe tener en cuenta que cada línea pertenece a una de las estaciones, es decir, la primera línea de código guarda la información del primer nodo del grafo (en la configuración actual, el nodo 0). La estructura de cada línea es la siguiente:
```
nombreDeLaEstacion,linea1,linea2...
```
Donde se pueden agregar tantas lineas como sea necesario para la estación correspondiente (por lo menos una linea es necesaria).

## Información de Precios
Este asset ubicado en [precios.txt](/app/src/main/assets/precios.txt) tiene la función de guardar y cargar la información de los precios empleados en la app, al igual que en el asset de estaciones, cada línea del texto guarda información, en este caso, de un precio específico. 

Cada línea del texto guarda información con la siguiente estructura:
```
titulo,descripcion,precioAbordaje,precioTransbordo
```
>[!Important]
>Los precios de abordaje y transbordo deben estar escritos en formato int o double.

