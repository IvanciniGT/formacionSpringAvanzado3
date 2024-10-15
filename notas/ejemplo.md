# Ejemplo para la formación

Vamos a montar un sistema para controlar diccionarios y palabras con sus significados.

Quiero una app de consola que me permita suministrar una palabra, un idioma y me muestre los significados.

    $ buscarPalabra ES manzana
    manzana: Fruto del manzano

## Cuántos proyectos monto para esto? 3

Mínimo 3 proyectos.En java: 3 repos de GIT, 3 archivos maven (pom.xml)

- Proyecto 1 : Interfaz gráfica (frontal)
- Proyecto ???
- Proyecto 2 : Gestión de los diccionarios y las palabras (y los idiomas)

```java

package com.curso.aplicacion;
import com.curso.diccionario.SuministradorDeDiccionarios;      
import com.curso.diccionario.Diccionario;                      
//import com.curso.dictionary.SuministradorDeDiccionariosFactory; // Interfaz
//import com.curso.diccionario.SuministradorDeDiccionariosDesdeFicheros; // ES LA SENTENCIA DE MUERTE DEL PROYECTO. Me acabo de cagar en el principio de inversión de dependencias.

// ES LA SENTENCIA DE MUERTE DEL PROYECTO. Me acabo de cagar en el principio de inversión de dependencias.

public class Aplicacion {
    public static void main(string[] args){
        ...
    }
    public void procesarPeticion(String idioma, String palabra, SuministradorDeDiccionarios suministrador){ // Inyección de dependencias
        //SuministradorDeDiccionarios suministrador = SuministradorDeDiccionariosFactory.getInstance();
        //SuministradorDeDiccionarios suministrador = new SuministradorDeDiccionariosDesdeFicheros("/diccionarios");
        if(suministrador.existeDiccionario(idioma)){
            Optional<Diccionario> potencialDiccionario = suministrador.getDiccionario(idioma);
            Diccionario diccionario = potencialDiccionario.get(); // Si no hay nada (está vacio -> lanza NullPointerException)
            if(diccionario.existe(palabra)){
                Optional<List<String>> significados = diccionario.getSignificados(palabra);
                if(significados.isPresent()){ // isEmpty()
                    for(String significado : significados.get()){
                        System.out.println(palabra + ": " + significado);
                    }
                } else {
                    System.out.println("No se han encontrado significados para la palabra " + palabra);
                }
            } else {
                System.out.println("La palabra " + palabra + " no existe en el diccionario " + idioma);
            }
        } else {
            System.out.println("No existe el diccionario " + idioma);
        }

    }

}

package com.curso.diccionario;

public interface Diccionario {
    public String getIdioma();
    public boolean existe(String palabra);
    public Optional<List<String>> getSignificados(@NonNull String palabra);
}

public interface SuministradorDeDiccionarios {
    public boolean existeDiccionario(String idioma);
    public Optional<Diccionario> getDiccionario(String idioma);
}

package com.curso.diccionario;

import com.curso.diccionario.Diccionario;
import com.curso.diccionario.SuministradorDeDiccionarios;

public interface SuministradorDeDiccionariosFactory { // Desde JAVA 1.8
    static SuministradorDeDiccionarios getInstance(){
        return new SuministradorDeDiccionariosDesdeFicheros("/diccionarios");
    }
}

public class SuministradorDeDiccionariosDesdeFicheros implements SuministradorDeDiccionarios {

    public SuministradorDeDiccionariosDesdeFicheros(String carpetaBase){
        ...
    }

    public boolean existeDiccionario(String idioma){
        ...
    }

    public Optional<Diccionario> getDiccionario(String idioma){
        ...
    }

}

public class DiccionarioDesdeFicheros implements Diccionario {

    public DiccionarioDesdeFicheros(String rutaFichero){
        ...
    }

    public String getIdioma() {
        ...
    }

    public boolean existe(String palabra){
        ...
    }

    public Optional<List<String>> getSignificados(String palabra) {
        ...
    }
    /*
        Que devuelve si le paso al diccionario de ES la palabra ARCHILOCOCO?
        - null              \
        - Lista vacía       / Son ambiguas!
        - Exception     NUNCA JAMAS PUEDO USAR EXCEPCIONES PARA CONTROL DE FLUJO.
                        Una excepción es cuando sé que algo puede pasar pero no tengo forma de anticipar si va a pasar.
                        Una excepción es computacionalmente muy costosa: Lo primero que hace falta es hacer un volcado de la pila de llamadas: THREAD STACK DUMP
                        Tiene una cosa guay (al menos en JAVA) y es... que no es ambigua: throws NoSuchWordException
    
        Por convenio, desde JAVA 1.8, con la inclusión de los Optional, NUNCA UNA FUNCIÓN PUEDE DEVOLVER NULL = FATAL PRACTICA!
        Los optional no valen para evitar NullPointerException. Sirven para quitar la ambigüedad de las firmas de las funciones.
    */

}

```

    Aplicación --> SuministradorDeDiccionarios  <-- SuministradorDeDiccionariosDesdeFicheros
               --> Diccionario                  <-- DiccionarioDesdeFicheros
                ^
                DEPENDENCIA (import)

Tengo un sistema MAS MODULAR !

Al hacer esto, solo hemos postpuesto el problema.. se lo hemos pasado a otro.
Al final, alguien tiene que hacer un `new SuministradorDeDiccionariosDesdeFicheros("/diccionarios")`.

Aquí es donde entra la INVERSION DE CONTROL.

Si no soy yo el que va a crear las instancias de mis clases... ni a llamar a mis funciones... sino que lo va a hacer UN FRAMEWORK DE INVERSION DE CONTROL... que se encargue él de inyectar las dependencias... es decir, de pasar a las funciones que llame los objetos que necesite... creando incluso esos objetos si es necesario.

---

# La evolución del mundo web

Antiguamente cuándo empezamos a desarrollar apps web, el HTML se generaba en el servidor y se enviaba al cliente. HOY EN DIA ESTO ES IMPLANTEABLE (jsp, php, asp, aspx, etc... están totalmente obsoletos)

## Quiero montar el sistema de Animalitos Fermín!

Antiguamente:
- Un servidor de apps JAVA (weblogic, websphere) y un servidor de BD (oracle, mysql).
- Desde el servidor de apps, se generaba el HTML y se enviaba al cliente.
- Y montaría un sistema monolítico: Un único proyecto con todo el código. Y lo consideraría una buena práctica el operar así:
  - Todo enlazado hace que sea más fácil de operar, de construir, de desplegar, de mantener, de hacer pruebas, etc... Generar los informes que quiera, ya que tengo acceso a toda la información.

HOY EN DIA ESTO ES IMPLANTEABLE en muchos escenarios.

Hoy en día:

FRONTAL                                             BACKEND
-------------------------------------------------   ----------------------------------------------------------------------

Navegador Web                           <---JSON                programas de gestión de Animalitos
    v1.1.0            v.1.1.0
    Formulario de --> ServicioAnimalitos ---> Controlador REST
    creación de 
    animalitos

    Lógica de         Lógica de 
    captura           comunicaciones con un Backend

    JS: Angular, React, Vue, Svelte, etc...                     
App Android                                                     nuevoAnimalito(datos)

App iOS     v1.1.0                                              eliminarAnimalito(id)
                                                                             v.1.1.0        v1.1.0        v.1.2.0       v2.0.0
                                                                             Controlador -> Servicio ->  Repositorio -> BBDD
                                                                             Lógica de       Lógica de    Lógica de
                                                                             exposición      negocio    persistencia
                                                                                               ^
                                                                             Controlador REST -+
                                                                                               |
                                                                             Controlador WS ---+
                                                                                               |
                                                                             Controlador SOAP -+
                                                                                              
App IVR: Voz                                                    recuperarDatosAnimalito(id)               

Siri, Alexa, Google Home                                        programas de gestión de citas de veterinario
                                                                reservarCitaConVeterinario(fecha, hora, cliente)
App Desktop para los empleados


HTML Es un lenguaje de dominio específico para la presentación de información en un navegador web. Es un lenguaje de marcado de información. Hoy en día necesito lenguajes de dominio general para la transmisión de información (XML-fue ruina, JSON)

WebComponents: Estándar nuevo (hace 15 años) del W3C... para montar SPA (Single Page Applications)
Páginas que puedan mutar y que sean más interactivas.
Además, que sean reutilizables esos componentes.
Y los navegadores hoy en día TODOS soportan nativamente los WebComponents: <animalito id="12123"/>
Esta funcionalidad los navegadores lo exportan mediante lenguajes de programación (JS)


El Fermin, que tiene mucha Pasta.. y ojo para los negocios, le está yendo bien!

En v1.0.0 del sistema, un animalito tenía:                  BBDD                        REPO:
- Nombre                                                    - Nombre                        Recibo una edad -> Fecha de nacimiento
- Raza                           -->                        - Raza                          Al leer la FNacimiento -> Edad v.1.1.0
- Edad                                                      - Fecha de nacimiento              + v1.2.0 Opcionalmente admito
                                                                                                         tmb la fecha de nacimiento

Y ahora el Fermín dice: "Quiero que los animalitos tengan mejor una fecha de nacimiento"... nos lleva mucho mnto el cambiar la edad.




## Versionado de software

En el esquema está una versión lleva una nomenclatura del tipo 1.2.3

                    ¿Cuándo suben esos números?
    1   MAJOR       Breaking changes: Cambios que no respetan la compatibilidad hacia atrás
                        Básicamente cuando quito algo (con o sin reemplazo)
    2   MINOR       Nueva funcionalidad
                    O funciona marcada como obsoleta (deprecated)
                        + opcionalmente pueden venir arreglos de bugs.
    3   PATCH       BugFixes

Y es mi forma de comunicarme con el mundo exterior... y decirles lo que he hecho.


# Microservicio de diccionarios, idiomas, palabras... (API REST)

## Microservicio???

Lo que hablamos realmente es de ARQUITECTURA DE MICROSERVICIOS.
Es una forma de plantear el desarrollo de un sistema.

Hay varios puntos:
- Componentes (micro-servicios) TOTALMENTE INDEPENDIENTES/DESACOPLADOS
- Quiero poder desplegarlos/operarlos de forma independiente (PASTA, AGILIDAD)
- Quiero poder construir cada componente con la tecnología que mejor se adapte a sus necesidades (java, python, javascript, c#, c++, elixir)
- Esos componentes tienen que poder comunicarse mediante interfaces muy bien definidas, con protocolos que se soporten con independencia de la tecnología (HTTP, REST, SOAP, gRPC, AMQP, MQTT, WebSockets) 
    CORBA = Va totalmente asociado a JAVA
- Los datos (y su gestión debe estar descentralizada): Cada componente es responsable de sus datos. (No hay un único punto de acceso a los datos)
  - No hay una megabase de datos Oracle con 800 tablas relacionadas... NO!!!!
  - Habrá cosas que las guarde en mysql, oracle, mongo, redis, cassandra, elastic, etc...


---

# Entornos de producción

- Alta disponibilidad (HA): TRATAR de garantizar un determinado tiempo de actividad, normalmente pactado de antemano.

    90%
    99%
    99.9%
    99.99%      Básicamente aumento la disponibilidad mediante REDUNDANCIA

- Escalabilidad: Capacidad de ajustar la infra a las necesidades de carga de cada momento.

    App1: App departamental. CADA VEZ HAY MENOS! (aunque siguen ahí!!!)
                dia 1:      1000 usuarios
                dia 100:    1000 usuarios    NO NECESITO ESCALABILIDAD
                dia 1000:   1000 usuarios

    App2: App que va teniendo más éxito. (Cada vez hay más!)
                dia 1:      1000 usuarios
                dia 100:    10000 usuarios      NECESITO ESCALABILIDAD VERTICAL: MAS MAQUINA !!!
                dia 1000:   100000 usuarios

    App3: ESTO ES LO MAS NORMAL HOY EN DIA: INTERNET
                dia n: 100 usuarios
                dia n+1: 1000000 usuarios
                dia n+2: 1000 usuarios
                dia n+3: 10000000 usuarios
                
        Ni siquiera a nivel de días.. incluso por minutos

                Soy la web del telepi!
                00:00       0 estoy cerrado
                06:00       0 estoy cerrado
                10:00       3 usuarios
                13:00       100 usuarios
                14:00       1000 usuarios
                17:00       4 usuarios
                20:30 Madrid/Barça 1000000 usuarios
                23:00       0 usuarios

        Qué infra compro aquí???
            Si compro infra para cuando tengo 1000 usuario.. cuando llega el negocio a 1000000.. me quedo sin servicio. Y ES CUANDO GANO PASTA
            Si compro infra para cuando tengo 1000000 usuarios.. cuando no tengo tantos: ESTOY HACIENDO EL PRINGAO!!! Tirando pasta.

            SOLUCION: ESCALABILIDAD HORITZONTAL: MAS MAQUINAS (o menos) según la necesidad.

La infra puede costar mucho más dinero que el desarrollo del sistema.. Y de quién depende?
La forma de operar (escalar) una app está totalmente condicionada por la Arquitectura del sistema... que es responsabilidad del equipo de desarrollo.

COMO DESARROLLADOR, es lo primero en lo que tienes que pensar: COSTE DEL CICLO DE VIDA.
Si tengo un sistema que permite trabajar en cluster, podré hacer escalabilidad horizontal. Si no no.
Y otra cosa es cómo escalo horizontalmente:
- Necesito replicar en otro servidor TODO EL SISTEMA? (pues prepara maquinón)
- Puedo replicar solo una parte? (microservicios) (GUAY... ajusto costes)

---

# Metodologías ágiles

Uno de los objetivos es adaptarse a los cambios fácilmente: FLEXIBILIDAD

Cuál es la principal característica de una metodología ágil? Entregar software al cliente de forma incremental.

Ya no entrego como en la met. tradicionales cuando acabo el proyecto. Quiero estar entregando cada 2 semanas... o 4 semanas... EN PRODUCCION!!!! El objetivo es obtener FEEBACK del cliente.

El día 20 de comenzar un proyecto, subo a producción el sistema.
    Bueno... quizás subo solo el 5% de la funcionalidad... pero subo algo.

Claro.. lo que no me cuentan en los cursitos de metodologías ágiles es que las met. ágiles nos han resuelto muchos problemas que teníamos antes... pero han venido con sus propios problemas.

Qué ahora tengo que instalar en prod cada 3 semanas? Pues sí.
Y espera.. qué implica un paso a pro? PRUEBAS a nivel de pro. Y a su vez esto implica instalación en el entorno de PRUEBAS!

Y de donde sale la pasta? y los recursos? y el tiempo? para tantas pruebas e instalaciones? NO LA HAY !! 
NI PASTA, NI RECUSOS, NI TIEMPO.
Entonces? AUTOMATIZACION! Qué ? TODO: Las pruebas, las instalaciones, los empaquetados del software, etc...

Porque además las pruebas se multiplican!

Entrega 1: día 20
    5% de la funcionalidad...   -> Probar el 5% de la funcionalidad

Entrega 2: día 40
    +10% de la funcionalidad...  -> Probar el 10% de la funcionalidad nueva + el 5% de la funcionalidad antigua

>> Extraído del manifiesto ágil: 

El software funcionando es la MEDIDA principal de progreso. > DEFINIR UN INDICADOR PARA UN CUADRO DE MANDO! (1)

La medida principal de progreso es el software funcionando.

---------SUJETO---------------- -----------PREDICADO------
  NUCLEO                        -----------------------
--      ---------- ------------ ATRIBUTO o COPULA
ARTICULO  ADJETIVO  COMPLEMENTO PREPOSICIONAL

La forma en la que voy a medir qué tal vamos en el proyecto es mediante el concepto SOFTWARE FUNCIONANDO.

La pregunta es: Qué es SOFTWARE FUNCIONANDO? Qué hace lo que tiene que hacer.. que funciona, que es apto para producción.
Y la nueva pregunta es QUIEN DICE ESO? 
- El dueño del producto, EL CLIENTE? NI DE COÑA !!! POR DIOS !!!!
- LAS PRUEBAS . Al cliente le debe llegar algo que FUNCIONE! (1)

> Met. tradicionales.
HITO 1: 10-Octubre (**R1,R2,R3**)

    Y si llegaba el día 10 de octubre y no estaba el R3, qué pasaba?
    - Suenan las alarmas
    - Ostías pa'tos'la'os
    - Replanificación: Nueva fecha 15 de octubre
    - Vamos con retraso!

HITO 2: 10-Noviembre (R4,R5,R6)
HITO 3: 10-Diciembre (R7,R8,R9)

> Met. ágiles.
SPRINT 1: **10-Octubre** (R1,R2,R3)

    Y si llega el 10 de Octubre y no está el R3?
    - Suenan las alarmas
    - Ostías pa'tos'la'os
    - SUBIMOS A PRODUCCION R1 y R2
    - Y el R3, al siguiente Sprint
    - Vamos con retraso!

SPRINT 2: 10-Noviembre (R4,R5,R6)
SPRINT 3: 10-Diciembre (R7,R8,R9)

Sprints e Hitos son muy diferentes:
- Un Spring IMPLICA pasar a producción
- Solo planifico el siguiente Sprint. Mientras que en las met. tradicionales planifico todo el proyecto.

---

# DEVOPS

Cultura, una filosofía, un movimiento en pro de la AUTOMATIZACION de todo el ciclo de vida del software DEV --> OPS

              AUTOMATIZABLE?        HERRAMIENTAS
PLAN                x
CODE                x
BUILD               √
                                    JAVA: Maven, gradle, sbt, ant
                                    C#: MSBuild, dotnet, nuget
                                    JS: npm, yarn, webpack
-----------------------------------------------------------------------> Desarrollo Ágil
TEST
    diseño          x
    ejecución       √
                                    FRAMEWORKS DE AUTOMATIZACION: JUnit, TestNG, NUnit, MSTest, Jasmine, Jest, Mocha, Cucumber, etc...
                                    UI WEB: Selenium, Cypress, TestCafe, Katalon, etc...
                                    UI App mobile: Appium, Calabash, etc...
                                    UI App desktop: UFT, TestComplete, etc...
                                    Servicios Rest: Postman, SoapUI, RestAssured, ReadyAPI, Karate, etc...
                                    Rendimiento: JMeter, Gatling, LoadRunner, etc...
                                    Calidad de código: SonarQube, CheckStyle, PMD, FindBugs, etc...
                                    Cobertura de código: JaCoCo, Cobertura, etc...
    Pero claro.. y dónde hago las pruebas?
       En la máquina del desarrollador ejecuto las pruebas? NUNCA... no me fío, me valen mierda! La máquina del desarrollador está MALEA!
       En la máquina del tester?                            NUNCA... no me fío, me valen mierda! La máquina del tester está MALEA!
       En un entorno de pre, creado al inicio del proyecto? NUNCA... no me fío, me valen mierda! El entorno de pre está MALEA!
                                                            Después de 50 instalaciones ese entorno está MALEA!
       Hoy en día usamos entorno de pruebas efímeros: Crear > Usar > Destruir
        Quién me echa una mano cojonuda con esto? CONTENEDORES
-------------------------------------------------------------------------> Integración Continua: Continuous Integration CI
  *Tener CONTINUAMENTE la última versión de mi programa instalada en un entorno de INTEGRACION sometida a pruebas AUTOMATIZADAS

    CUAL ES EL PRODUCTO DE UN PROCESO DE INTEGRACION CONTINUA? UN INFORME DE PRUEBAS EN TIEMPO REAL
        Para qué? Para saber qué tal vamos (1)

RELEASE - Poner en manos de mi cliente la última versión de mi software
    app Android: Dejar la app en la Play Store
    Subir mi microservicio o librería a un Artifactory o Nexus
    Subir una imagen de contenedor a un Registry
                    √
-------------------------------------------------------------------------> Entrega Continua: Continuous Delivery CD
                                    Ansible, Puppet, Chef, SaltStack, etc...
DEPLOY              √               Terraform, Vagrant, Ansible, Scripts de la bash, Kubernetes
-------------------------------------------------------------------------> Despliegue Continuo: Continuous Deployment CD
OPERATE             √               Kubernetes, Rancher, OpenShift, Mesos, etc...
MONITOR             √               Prometheus, Grafana, Nagios, Zabbix, Elastic, etc...
-------------------------------------------------------------------------> He adoptado una cultura DEVOPS completa


---

# Pruebas al backend del sistema de Fermin:

Pruebas al Servicio de animalitos... en su función 
    Animalito nuevoAnimalito(DatosDeNuevoAnimalito datos);

Imaginad que al dar de alta un animalito necesito que:
- 1. Se persista en el repositorio, que va a proveer un id
- 2. Se solicite el envío de un email... a una lista de subscriptores

¿Cómo pruebo esa función? Depende del nivel al que haga la prueba

## Unitaria

DADO        Dado 4 hierros mal soldaos sobre los que apoyar el servicio (REPO DE MENTIRIJILLA)
            Que cuando le pasen los datos de un animal devuelva siempre los mismos datos... y el id 33 (STUB)
            ~~Y dado un servicio de envío de emails, al que cuando le pidan que envíe un email no haga nada (eso seguro no falla). DUMMY~~
            Necesitamos un ESPIA: SPY
            Los datos de un animalito.
CUANDO      llamo a la función nuevoAnimalito con los datos del animalito ese
ENTONCES    Devuelve un objeto que tiene:
                - Los mismos datos que le habíamos pasado
                - Y el id 33
            Garantizar que se ha solicitado al servicio de envío de emails que envíe un email, con los datos del animalito.
            - con asunto: Nuevo animalito
            - a la lista de subscriptores 
            Garantizar que se ha solicitado al repositorio que persista el animalito correcto (otro SPY+STUB = MOCK) 

```java

public interface ServicioDeEnvioDeEmails {
    public void enviarEmail(String asunto, String cuerpo, List<String> destinatarios);
}

public class ServicioDeEmailsSpy implements ServicioDeEnvioDeEmails { 
            // Y esto vale luego para el sistema? NO... ni el bastidor.. ni el sensor.. pero lo necesito para las pruebas
    private boolean seHaLlamado = false;
    private String asunto;
    private String cuerpo;
    private List<String> destinatarios;

    public void enviarEmail(String asunto, String cuerpo, List<String> destinatarios){
        seHaLlamado = true;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.destinatarios = destinatarios;
    }

    public boolean seHaLlamado(){
        return seHaLlamado;
    }

    public String getAsunto(){
        return asunto;
    }

    public String getCuerpo(){
        return cuerpo;
    }

    public List<String> getDestinatarios(){
        return destinatarios;
    }
}

public Animalito nuevoAnimalito(DatosDeNuevoAnimalito datos){
    Animalito animalito = new Animalito();
    animalito.setNombre(datos.getNombre());
    animalito.setRaza(datos.getRaza());
    animalito.setEdad(datos.getEdad());
    animalito = repositorioDeAnimalitos.persistir(animalito); // Solicitar la persistencia
    servicioDeEnvioDeEmails.enviarEmail("Nuevo animalito", "Se ha dado de alta un nuevo animalito", listaDeSubscriptores); // Solicitar el envío de email
    return animalito;
}
```

## Integración

¿cuál de las 2?

### Integración con el servicio de envío de emails

DADO        Dado 4 hierros mal soldaos sobre los que apoyar el servicio (REPO DE MENTIRIJILLA)
            Que cuando le pasen los datos de un animal devuelva siempre los mismos datos... y el id 33 (STUB)
            Y dado un servicio de emails de verdad de la buena!
            Los datos de un animalito.
CUANDO      llamo a la función nuevoAnimalito con los datos del animalito ese
ENTONCES    Devuelve un objeto que tiene:
                - Los mismos datos que le habíamos pasado
                - Y el id 33
            Garantizar que en la bandeja POP3 o IMAP del usuario hay un email con el asunto "Nuevo animalito" y el cuerpo "Se ha dado de alta un nuevo animalito" que antes no estaba. 

### Integración con el repositorio

DADO        Dado un repositorio de verdad de la buena!
            Y dado un servicio de envío de emails, al que cuando le pidan que envíe un email no haga nada (eso seguro no falla). DUMMY
            ^ Ahora no quiero mock... quiero un dummy... para que la prueba vaya RAPIDO (FAST)
            Los datos de un animalito.
CUANDO      llamo a la función nuevoAnimalito con los datos del animalito ese
ENTONCES    Devuelve un objeto que tiene:
                - Los mismos datos que le habíamos pasado
                - Y el id que me ha devuelto el repositorio
            Garantizar que si pido al repo que me devuelva el animalito con el id que me ha devuelto, me devuelve el animalito correcto.

## Sistema

DADO       Dado un sistema de verdad de la buena! con un repo de verdad de la buena! y un servicio de emails de verdad de la buena!
           y una bbdd de verdad de la buena!
           Los datos de un animalito.  
CUANDO     llamo a la función nuevoAnimalito con los datos del animalito ese
ENTONCES   Devuelve un objeto que tiene:
               - Los mismos datos que le habíamos pasado
               - Y el id que me ha devuelto el repositorio
           Garantizar que si miro en la BBDD el animalito con el id que me ha devuelto, me devuelve el animalito correcto.
           Garantizar que en la bandeja POP3 o IMAP del usuario hay un email con el asunto "Nuevo animalito" y el cuerpo "Se ha dado de alta un nuevo animalito" que antes no estaba.

Hago una prueba end-to-end
    DEL PRIMERO AL ULTIMO DE LA CADENA

---

PREGUNTA:

Tengo este código:

```java
public Animalito nuevoAnimalito(DatosDeNuevoAnimalito datos){
    Animalito animalito = new Animalito();
    animalito.setNombre(datos.getNombre());
    animalito.setRaza(datos.getRaza());
    animalito.setEdad(datos.getEdad());
    RepositorioDeAnimalitos repositorioDeAnimalitos = new RepositorioDeAnimalitosDeVerdadDeLaBuena();
    animalito = repositorioDeAnimalitos.persistir(animalito); // Solicitar la persistencia
    ServicioDeEmails servicioDeEnvioDeEmails = new ServicioDeEmailsDeVerdadDeLaBuena();
    servicioDeEnvioDeEmails.enviarEmail("Nuevo animalito", "Se ha dado de alta un nuevo animalito", listaDeSubscriptores); // Solicitar el envío de email
    return animalito;
}
```

Es posible a ese código hacerle una prueba unitaria? Está soldado rígidamente al REPO y al SERVICIO DE EMAILS de verdad de la buena.
No tengo forma de darle el cambiazo para hacer la prueba... y poner en su lugar un REPO y/o un SERVICIO DE EMAILS de mentirijilla.

Por no cumplir mi código con el principio de inversión de dependencias... no puedo hacerle una prueba unitaria... ni de integración... solo de sistema.

VAYA PROBLEMON !

Imposible así ir a una metodología ágil... NO CUMPLO con los principios de las metodologías ágiles.

---

# Test-Double

- Dummy: Objeto que no se usa, pero que es necesario para la ejecución de la prueba.
- Stub: Objeto que devuelve valores predefinidos.
- Spy: Objeto que registra las llamadas que se le hacen.
- Mock: Objeto que verifica que se han hecho las llamadas esperadas.
- Fake: Objeto que simula el comportamiento de un objeto real, pero de forma simplificada.

Esto lo nombra y lo estudia pro primera vez nuestro amigo Martin Fowler.