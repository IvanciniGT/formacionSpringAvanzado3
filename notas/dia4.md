1º Repaso a la arquitectura de componentes de nuestro sistema

    Capa dominio
        Módulo: Definimos modelos y repositorios para editores
        Módulo: Definimos modelos y repositorios para búsqueda de palabras
        Módulo: Definimos modelos y repositorios para gestión de palabras y diccionarios
            * repositorios= Gestión básica de esos modelos.
        Módulo: Definimos entidades JPA y repositorios JPA
        Modulo: Implemente nuestro REPO de búsqueda de palabras contra JPA
        Modulo: Implemente nuestro REPO de gestión de palabras y diccionarios contra JPA
        MODULO: para implementar mi repo de editores contra LDAP

    Los repos JPA me dan métodos CRUD para trabajar contra BBDD relacionales
    Nuestros repositorios me dan los métodos que nosotros queremos tener (y exponer) para trabajar contra palabras y diccionarios

            BUSQUEDAS
                modelos
                repositorios (API)
                    Funciones para apañarme con las búsquedas de palabras
                                                            vv
                                                        Implementación de mi API de búsquedas
                                                            vv
                                                        API REST (funciones CRUD)
                                                            vv
                    BBDD Relacional  >> Proceso batch >> ElasticSearch
                         ^^
                      API JPA (funciones CRUD)
                         ^^
                Implementación de mi API de gestión de palabras y diccionarios
                         ^^
            GESTION      ^^
                modelos  ^^
                repositorios (API)
                    Funciones para apañarme con la gestión de palabras y diccionarios

2º Revisar la lógica que implementamos en NUESTRO repositorio

    Borrar un significado?
        Qué pasa con el orden del resto de significados si borro uno? Hay que cambiarles la propiedad NUMERO... pregunta. 
           - Es el repo el sitio adecuado para hacer eso?   TOTALMENTE
           - O eso debería ir en capa de negocio?           NUNCA EN LA VIDA

3º Excepciones

Ahora mismo en la implementación tenemos 5 implementaciones de nuestros repos.
Cada uno con n funciones (2-10) que pueden lanzar un InvalidArgumentException si los datos que se reciben no son adecuados.
Pero... como están implementados basándose en los repos JPA, lo que lanzarían ahora mismo es excepciones de JPA.

Me toda en todas esas funciones meter try{}catch que controlen las excepciones de JPA y las conviertan en InvalidArgumentException.
MUCHO TRABAJO... y QUE A NADIE SE LE OLVIDE EN UNA DE ESAS FUNCIONES!

Aquí es donde entra la PROGRAMACION ORIENTADA A ASPECTOS.
    - Spring AOP < Proxys 

Uno de los usos guays de esto es la gestion CENTRALIZADA de excepciones.


PROXY: Creamos una clase en tiempo de ejecución, que intercepta todas las llamadas a otra clase... BASICAMENTE NOS ESTAMOS AUTO-HACIENDO UN ATAQUE MAN IN THE MIDDLE A NOSOTROS MISMOS!


    ClaseB ---> Clase A
            ^
            Interceptar esa llamada y hacer algo antes y después de la llamada

    ClaseB ---> Proxy ---> Clase A

        La clase Proxy tiene las mismas funciones que la clase A (identicas) y dentro, en su implementación las llama a las funciones de la clase A.


        ```java 

        public class ClaseA { // << Esta clase se entera de algo? de que le están jaqueando? NO
            public void funcion1() {
                System.out.println("Hola");
            }
        }
        public class ClaseB {
            ClassA claseA; // <<< En lugar de pasarle una instancia de ClaseA, le paso una instancia de Proxy? Se entera?
            public void unaFuncion() {
                claseA.funcion1();
            }
        }
        public class Proxy { // Nos encanta esto! Si encima puedo hacer que estas clases se generen dinámicamente, MA-RA-VI-LLO-SO! = AOP
            ClassA claseA;
            public void funcion1() {
                System.out.println("Antes de llamar a funcion1");
                try{
                    claseA.funcion1();
                } catch (Exception e) {
                    System.out.println("Ha habido un error");
                }
                System.out.println("Después de llamar a funcion1");
            }
        }

        ```

Podemos crear una configuración en Spring que nos genere estos proxies automáticamente para todas las clases que queramos: @Aspect
```java
@Aspect // LENGUAJE DECLARATIVO: Spring, quiero que todas las funciones gestionen las excepciones de esta forma.
public class ExcepcionesAspect {
    @Around("execution(* com.curso.diccionarios.repositorio.impl.*.*(..))")
    public Object interceptar(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (JpaDataIntegrityViolation e) {
            throws InvalidArgumentException("Error de datos");
        }
    }
}
```

Y esto, si es que Spring lee esta clase en el arranque (ComponentScan.. y los paquetes @SpringBootApplication) , Spring crea Proxies para todas las clases de ese paquete, añadiendo esa funcionalidad que pedimos en todas la funciones que le haya dicho... En nuestro caso, en TODAS.

Ventajas enormes:
- 1. En cada función me centro en su HAPPY_PATH: Lógica básica!
    Por tanto se entenderán, escribirán y mantendrán super bien! Al menos mucho mucho mucho más fácil que si tengo que meter try{}catch en todas las funciones.
- 2. Es mucho más robusto! Si me olvido de meter un try{}catch en una función, no pasa nada! El proxy se encargará de ello.
- 3. Es mucho más fácil tener 1 único sitio donde gestionar las excepciones. Si quiero cambiar el mensaje de error, lo cambio en un único sitio.

---


Ciencias de la computación = Física o Matemáticas es una CIENCIA EXACTA. Con leyes y principios que se cumplen siempre. PRINCIPIO = LEY

Ingeniería de software = Ingeniería civil o mecánica. No es una ciencia exacta. No hay leyes que se cumplan siempre. PRINCIPIO = Reglas por las que decido regirme en mi trabajo.
    SOLID, KISS, DRY, SOC, YAML = No son leyes que se cumplan siempre. Son reglas que me ayudan a tomar decisiones en mi trabajo.

Ingeniería: Crear o producir algo (software) teniendo en cuenta:
- Limitaciones de tiempo            \
- Limitaciones de recursos           > Presentes y futuras
- Limitaciones de conocimiento      /
- Limitaciones impuestas por normativas, leyes, etc.
- Condicionantes externos: Existen por ahí piratones que me van a hackear, por ejemplo.

Spring me ofrece una anotación especial para usar aspectos en peticiones REST, a nivel de controlador:
    @ControllerAdvice

```java
    @ControllerAdvice
    public class ExcepcionesControllerAdvice {
        @ExceptionHandler(JpaDataIntegrityViolation.class)
        public ResponseEntity<String> gestionarExcepcion(JpaDataIntegrityViolation e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de datos");
        }
    }

```

---


BBDD relacionales

Guardan datos estructurados.
Qué tal van las búsquedas en una BBDD?
    - Proc. más BASICO que tienen es FULL SCAN: Recorrer toda la tabla buscando lo que le pides. EFICIENTE? NO.
    - Proc. más AVANZADO: BUSQUEDA BINARIA. BUSCAR EN UN DICCIONARIO!
      Para poder hacer esto, hay un requisito: DATOS ORDENADOS
        Ordenar bajo demanda es lo peor que puedo pedir a un ORDENADOR!
        Solución: CREAR INDICES
            INDICE = Copia (Duplicado) ordenada de los datos.
        Las BBDD Recopilan ESTADISTICAS! Para optimizar los 2 primeros cortes de un índice.

TITULOS
----------------------
1 Tortilla-de-patatas
2 Patatas con garbanzado
3 Papatas a lo pobre
4 Pulpo con patatas

    patatas -> FULLSCAN <- LIKE %LOQUESEA
                UPPER(TITULO) = LIKE "%" + UPPER('patatas')

Las BBDD son expertas en INDICES DIRECTOS.
Pero hay un concepto que se llama "INDICE INVERSO" que es un poco más complicado.
    
    tortilla    1(1)
    patata      1(3), 2(1), 3(1), 4(3)
    pobre       3(4)
    pulpo       4(1)

    patatas -> INDICE INVERSO -> 1, 2, 3, 4
                está en el índice inverso "patata"? Ahí si puedo aplicar una BUSQUEDA BINARIA.

    En general las BBDD son una mierda gestionando indices inversos. Lo que gestionan bien son los indices directos.

    Y para los inversos hay otro concepto que se llama "INDEXADORES" que es un poco más complicado: Elastisearch, Solr, etc.

---

Guardo los datos en BBDD Relacional.
    Necesito un campo numero
Guardo los datos en MongoDB... que lo que guarda es un JSON
    {
        "palabra": "patata",
        "significados": [
            {
                "definicion": "Plato"
            }
        ]
    }

---                                                                          Importación
                                                                                vv
----------Frontal-----------------   -------------------------Back-----------------------------
Formulario WEB  > Servicio Frontal > ControladorREST > Servicio de negocio > Repo Dominio > BBDD (PL/SQL)
 * Campo DNI
                                                        *****************     !!!!!!!!!
**************     **************                                             IMPORTANTE
                   **************
                   **************
    ^
 Lógica            Comunicarla        Lógica de exposición   Lógica de negocio  Lógica de los   Persistir los datos
 recoger            al back             el servicio             negocio          datos
 información                          por protocolo REST
                                                                              ^^^^^^^^     ^^^^^

Me dicen, como requisito! que solo puedo poner la validación de la estructura del DNI en un único sitio.
Cuál sería?

Voy a dejar a la gente que ataque directamente a la BBDD?
    SQL (carga en batch por las noches)? SI -> BBDD es la que deben tener el chequeo.
Si no voy a dejar a la gente que ataque directamente a la BBDD? (QUE SERIA LO MAS SENSATO!)
    El chequeo va a capa de dominio

Un DNI es un DNI. En mi negocio y en cualquier otro negocio. La lógica de tener 8 numeritos y una letra no es lógica de negocio. Es lógica de estructura de datos.
Igual que una fecha es dia, mes y año. Eso es lógica de estructura de datos.
Igual que una palabra tiene significados ordenados. Eso es lógica de estructura de datos.

DNI =  de 1 a 8 dígitos seguido de una letra.

---
Yo tengo que poner la validación en el único sitio donde debe ir!
Adicionalmente puedo poner validaciones de cortesía!


---


BAJO NIVEL (Desde abajo) - Capa dominio -> Cuando tengo un sistema MUY PERO QUE MUY CLARO! Puedo empezar por abajo

Si el sistema no lo tengo tan claro... me suele interesar más empezar por arriba: Capa de Controladores REST
Y hacer lo que llamamos API DRIVEN DEVELOPMENT
    - Definir la API REST que quiero tener
    - Implementar esa API REST
    - Implementar la lógica de negocio que necesito para esa API REST
    - Implementar la persistencia de datos que necesito para esa lógica de negocio

Pero además vamos a aplicar otra cosa que nos va a venir GUAY!
    - TDD (Test Driven Development)

        TDD = Test First + Refactor
        TDD: Voy a ir creando test.. y voy a hacer lo mínimo que necesito hacer para que cada test pase.
        La idea es ir creando juntos las funciones y los tests.
        Me aseguro así que cada funcionalidad tiene un test que la cubre.

    - Vamos a partir de Test de integración por estar en capa de controlador
          Cliente HTTP -> [Servidor de aplicaciones (TOMCAT) -> Controlador REST ]-> Servicio de negocio -> Repo de dominio -> BBDD 