
# JPA

Una especificación de JEE para persistencia directa de objetos JAVA a BBDD Relacionales

Echamos mano de un ORM: (object Relational Mapping)  : HIBERNATE

Nosotros no trabajaremos con hibernate directamente. Spring pone una capa por encima: Spring-JPA

---

# Programación funcional

Es otro paradigma de programación. Hay muchos:
- Imperativo                Cuando doy ordenes que se deben ejecutar secuencialmente (IF, FOR, WHILE)                       √
- Procedural                Cuando el lenguaje me permite agrupar ordenes en funciones que puedo invocar posteriormente     √
                                Para que?
                                    - Reutilización
                                    - Mejorar la estructura del código (legibilidad)
                                    - Para pasar lógica a otra función
- Orientación a Objetos     Cuando el lenguaje me permite definir mis propios tipos de datos (clases), con sus              √
                              propiedades(atributos) y sus funciones especiales:

                              String
                              List
                              ZonedTimeDate 

                              Usuario
                              Diccionario
                              DiccionarioRepositorio

                            Otros conceptos: Herencia, polimorfismo, sobrecarga...
- Declarativo               Cuando me centro en lo que quiero y no en cómo hacerlo                                          √
                            @Anotaciones

- Paradigma funcional       Cuando el lenguaje me permite que una variable apunte a una función y posteriormente ejecutar esa
                            función desde la variable, entonces digo que tengo un lenguaje que soporta programación funcional. 
                            El tema no es lo que es... que es una chorrada.
                            El tema importante es lo que puedo llegar a hacer cuando el lenguaje me permite esto:
                                - Pasar funciones a otras funciones como argumentos
                                - Crear funciones que devuelvan funciones como resultado
                                - Y AQUI ES DONDE LA CABEZA EXPLOTA !

```java
String texto = "hola";
```
Asignar una variable String a un valor

La fecha apunta hacia el valor... no hacia la variable. Una variable no es una cajita donde meto cosas.
En C Si... En C++ también. En Java, Python, JS, una variable es una REFERENCIA (más el concepto de puntero)

- "hola"        CREA UN OBJETO DE TIPO STRING en RAM con valor "hola"
- String texto  CREAR UNA VARIABLE que puede apuntar a objetos de TIPO String... llamada texto.
                Si la RAM la imaginamos como un cuaderno de cuadrícula, una variable en JAVA, (JS, PY) es una post-it
                En el cuaderno escribo "hola" (no en postit)... En el postit, lo que escribo es "texto"
                Y el postit lo pego en el cuaderno al lado del hola!
- =             Asigno la variable al dato

```java
texto = "adios";
```

- "adios"      CREA UN OBJETO DE TIPO STRING en RAM con valor "adios"
               DONDE lo crea? En el mismo sitio donde estaba apuntado "hola" o en otro? En otro sitio.
               En este punto cuantos objetos de tipo String tengo en RAM? 2: "hola" y "adios"
- texto =      Desasociar la variable de donde estaba asociada y MOVERLA (EL POSTIT-> VARIA SU POSICION)
               y la pegamos al lado del nuevo valor "adios".
               Y en este momento, el objeto "hola" en RAM queda huérfano de variable y se convierte en GARBAGE
               Y quizás o no en algún momento (o no) entre el Recolector de Basura (GC) y lo elimine.

```js
var texto  = "hola";
texto = 4; // FUNCIONA GUAY
```

ESO ES JAVA ! JAVA 11.
Pero el var de java no es igual al var de js..
El JS las variables NO TIENE TIPO. En JAVA SI. Y dónde está el truco?

```java
var texto  = "hola";
texto = 4; // ERROR DE TIPOS. La variable texto es de tipo STRING y no puede asignarse a un dato de tipo int.
```
En java, al usar la palabra var, el compilador INFIERE EL TIPO del primer valor que se asigna a la variable.
```java
Map<String, List<LocalDateTime>> mapa = ...;
var mapa =...;


Funciones lambda.
Streams
