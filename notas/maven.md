# Maven

Es una herramienta de automatización de tareas habituales en proyectos de software (principalmente, aunque no exclusivamente, en proyectos JAVA).

Me permite:
- Procesar unos ficheros de recursos (xml, yaml, properties...) aplicándole variables que tengo definidas dentro de los ficheros.
- Compilar el proyecto
- Compilar mis pruebas
- Ejecutar mis pruebas
- Empaquetar mi proyecto (.jar, .war, .ear)
- Generar una imagen de contenedor
- Mandar mi código a sonarqube
- Aplicar un formateador para que mi código se vea bonito
- Generar informes de cobertura de código
- Generar la documentación (javadoc) de mi proyecto
- Ah... y descargar dependencias de un repo de dependencias (MAVEN CENTRAL, Artifactory, Nexus, Github, Gitlab, etc.)

Toda tarea en maven se ejecuta por un plugin.

2 conceptos:
    - Ciclo de vida de un proyecto según maven:
       compile -> test-compile -> test -> verify -> package -> install
    - Cada plugin me ofrece tareas que puede ejecutar.
Puedo asociar tareas de los plugins a etapas del ciclo de vida. Por defecto maven trae configurado un ciclo de vida por defecto. Pero puedo definir mi propio ciclo de vida.

En un proyecto típico maven, encontramos la siguiente estructura de directorios:
     
     proyecto/
        ├── src/
        │   ├── main/
        │   │   ├── java/
        │   │   ├── resources/
        │   └── test/
        │       ├── java/
        │       └── resources/
        ├── target/                     // NOTA: Esta carpeta no la queremos en el repo de git
        │   ├── classes/
        │   ├── test-classes/
        │   ├── surefire-reports/
        │   └── nombre-del-proyecto-version.jar
        ├── pom.xml


El archivo pom.xml contiene:
- Los datos identificativos del proyecto (Coordinadas del proyecto):
  - groupId     Grupo de proyectos al que pertenece este proyecto
  - artifactId  Identificador único del proyecto dentro del grupo
  - version     Versión del proyecto
- Otros metadatos del proyecto (nombre, descripción, licencia, url del proyecto, etc.)
- Propiedades del proyecto (variables que puedo usar en el resto del archivo y/o variables de configuración para los plugins)
- Plugins que quiero usar y sus configuraciones
- Dependencias que quiero usar y sus versiones

poor defecto, asociado al flujo de vida estándar de maven, tenemos las siguientes fases de interés:
- compile       Compila lo que hay en src/main/java/ generando archivos .class que deposita en target/classes
                Copia los recursos de src/main/resources/ en target/classes
     ^
- test-compile  Compila lo que hay en src/test/java/ generando archivos .class que deposita en target/test-classes
                Copia los recursos de src/test/resources/ en target/test-classes 
     ^
- test          Solicita al plugin Surefire que ejecute las pruebas que hay en target/test-classes.
                Nota: El plugin Surefire busca clases que empiecen o acaben por Test y las ejecuta mediante JUnit.
                Depende de la versión del plugin Surefire, podré ejecutar pruebas de JUnit 4 o JUnit 5.
                Además, genera un informa de pruebas, que por defecto se guarda en target/surefire-reports
     ^
- package       Si trabajamos en un proyecto que empaquete como .jar, crea un ZIP con el contenido de target/classes y lo guarda en
                  target/nombre-del-proyecto-version.jar
     ^
- install       Copia mi archivo .jar (el que se ha generado) en mi carpeta .m2 de forma que pueda ser usado por otros proyectos 
                maven dentro de mi máquina.

- clean         Borra la carpeta target

---

# Carpeta .m2 de maven

Cuando instalo maven se crea una carpeta llamada .m2 (y por ende OCULTA) dentro de mi carpeta de usuario (c:\Users\miUsuario en Windows, /home/miUsuario en Linux).
En esa carpeta existe un archivo llamado settings.xml que contiene la configuración global de maven, con cosas como por ejemplo:
- Para descargarte dependencias vas a usar este repositorio central: https://repo.maven.apache.org/maven2 **(Maven Central)**
                                                                     https://mi.artifactory.com/mi-repo
- Y como trabajarás por HTTP, puede ser que en la empresa necesites salir por un proxy. Si es así, el proxy es este: http://mi.proxy.com:8080. Y además el proxy te pedirá usuario y contraseña.

Adicionalmente, en esta carpeta es donde maven busca las dependencias que necesita para compilar mi proyecto. Si no las encuentra, las descarga de los repositorios que tenga configurados en el archivo settings.xml, y las deja en esta carpeta.

---

EXTRA: MAVEN AVANZADO

En maven podemos tener proyectos MULTI-MÓDULO. 

Es decir, un proyecto que tiene varios subproyectos. 
Cada subproyecto es un proyecto maven en sí mismo, con su propio pom.xml, su propio ciclo de vida, sus propias dependencias, etc.

El tenerlo organizado de esta forma, me permite:
- Definir a nivel del proyecto padre configuraciones(incluyendo dependencias) que heredan todos los subproyectos.
- Gestionar desde el proyecto padre el ciclo de vida de todos los subproyectos. (Puedo hacer un compile o un package de todos a la vez)
- Definir a nivel de los proyectos hijos configuraciones(incluyendo dependencias) específicas para cada uno de ellos.
- Gestionar desde el proyecto hijo su propio ciclo de vida.

En un escenario como este, la estructura de carpetas cambia ligeramente:
     
     proyecto/
        ├── subproyecto1/
        │   ├── src/
        │   │   ├── main/
        │   │   │   ├── java/
        │   │   │   └── resources/
        │   │   └── test/
        │   │       ├── java/
        │   │       └── resources/
        │   ├── target/                     // NOTA: Esta carpeta no la queremos en el repo de git
        │   │   ├── classes/
        │   │   ├── test-classes/
        │   │   ├── surefire-reports/
        │   │   └── nombre-del-proyecto-version.jar
        │   └── pom.xml
        ├── subproyecto2/
        │   ├── src/
        │   │   ├── main/
        │   │   │   ├── java/
        │   │   │   └── resources/
        │   │   └── test/
        │   │       ├── java/
        │   │       └── resources/
        │   ├── target/                     // NOTA: Esta carpeta no la queremos en el repo de git
        │   │   ├── classes/
        │   │   ├── test-classes/
        │   │   ├── surefire-reports/
        │   │   └── nombre-del-proyecto-version.jar
        │   └── pom.xml
        ├── pom.xml    <        En este pom, declaro los submodulos que tiene mi proyecto. 
                                Doy de alta las carpetitas de los subproyectos.ç
---

Nota:

Lo guay además sería que cada carpeta estuviera en su propio REPO DE GIT:
- Repo de git para subproyecto1
- Repo de git para subproyecto2

En GIT hay un concepto llamado SUBMÓDULOS que me permite tener un repo de git dentro de otro repo de git.
Podría tener un tercer REPO de para el proyecto padre, que contenga como submódulos los otros dos repos.

Si hago un clone del padre, me traigo el padre y los submódulos. Si hago un clone de un submódulo, me traigo solo el submódulo.

---

Alternativas a Maven:

- gradle: Hoy en día casi se está imponiendo a maven. Es más moderno, más potente, más flexible, más fácil de usar.
- sbt: Para proyectos Scala

---

Kotlin? Otro lenguaje de programación (En el mundo Android ha reemplazado a Java)
    Pero cuidado.. no os despistéis... SPRING el año pasado acabó su migración completa a Kotlin
Scala? Esto es otro lenguaje de programación (al mundo big data le encanta)

PYTHON
        def generarInforme(titulo, datos):
            pass

---

JAVA es un lenguaje con una cantidad de mierdas enormes en su gramática.
Y por eso (y otros motivos)

---

Qué tal le va a JAVA como lenguaje? En decadencia 

Por qué?
- Es cierto que java tiene algunas mierdas en la gramática.. Vivimos con ellas... y poco a poco se van arreglando. Demasiado poco a poco.
- El problema grande de JAVA fué caer en manos de quien cayó. Oracle... conocido como El hermano informático de THANOS (El destructor de software). Es una experta en destruir software.
        MySQL -> Postgres
         |-----> MariaDB (continuación real de MySQL) 

        OpenOffice (Sun Microsystems) -> LibreOffice (continuación real de OpenOffice)

        Hudson --> Jenkins

        Lo mismo pasó con JAVA

---

Historial de versiones de JAVA

Java 1.1 ---- Año 1997
Java 1.2 ---- Año 1998
Java 1.3 ---- Año 2000
Java 1.4 ---- Año 2002
Java 1.5 ---- Año 2004     
Java 1.6 ---- Año 2006     Cada 2 años una versión
---- Compra de Sun Microsystems por parte de Oracle ---- (al que JAVA le importaba mierda)
Java 1.7 ---- Año 2011     5 años después
Java 1.8 ---- Año 2014     3 años después
------> Se le ocurrió a Oracle otra cosita... Oracle JDK de pago        <---- GOOGLE A LA GUERRA y se llegaron a acuerdos
Java 9 ---- Año 2017     3 años después --- En 11 años, 3 versiones (y menudos 11 años!)
Java 10 --- Año 2018     1 año después
Java 11 --- Año 2018     
Java 12 --- Año 2019     1 año después
Java 13 --- Año 2019     
Java 14 --- Año 2020     1 año después
Java 15 --- Año 2020
Java 16 --- Año 2021
Java 17 --- Año 2021
Java 18 --- Año 2022
Java 19 --- Año 2022
Java 20 --- Año 2023
Java 21 --- Año 2023
Java 22 --- Año 2024
Java 23 --- Año 2024


Acuerdo para sacar versiones de JAVA cada 6 meses.

La JVM se convirtió en un estándar.. Y hoy en día tenemos muchas implementaciones de la JVM:
- oracle JDK
- openJDK
- Eclipse temurin
- Amazon Corretto
- ...

El J2EE: Java Enterprise Edition (antes)
    Oracle lo donó a una fundación y se lo quitó de encima. La fundación se llama Jakarta. Y hoy en día el nombre JEE significa Jakarta Enterprise Edition.

Pero GOOGLE NO PERDONÓ!
- Lo primero extraer de su navegador chromium el motor de procesamiento de JS... para convertirlo en un programa independiente de su ejecución en un navegador. Esto es el proyecto NodeJS. Es el equivalente a la JVM para JS.
- Solicitó a la gente que estaba haciendo su nuevo entorno de desarrollo para Android (antiguamente era una versión tuneada de Eclipse)... pero hoy en día Android Studio (versión tuneada de IntelliJ IDEA, creada por la gente de JetBrains y que a Google le cuesta un pastón) que crearán un nuevo le lenguaje de programación para Android. Y así nació Kotlin.

con una peculiaridad:
- 1. Es un lenguaje creado por unos de los mayores expertos a nivel mundial en gramáticas de lenguajes de programación.
- 2. Los archivos
     .java -> compilar -> .class -> ejecuta (interpreta por la ) JVM
     .kt   -> compilar -> .class -> ejecuta (interpreta por la ) JVM
        Aprovechando toda la infraestructura de la JVM (que es brutal) y todo el eco sistema de software y librerías que hay alrededor de JAVA.
     .scala -> compilar -> .class -> ejecuta (interpreta por la ) JVM
        Aprovechando toda la infraestructura de la JVM (que es brutal) y todo el eco sistema de software y librerías que hay alrededor de JAVA.

---

# LOMBOK

Es una librería que me ayuda a generar mucho código boilerplate que normalmente yo tengo que escribir en JAVA.
Me ofrece una colección de más de 20 anotaciones que me permiten:
- Generar Getters y Setters en mis clases
- Generar constructores
- Generar métodos equals y hashcode
- Generar métodos toString
- Generar patrones builder para crear objetos
- Usar loggers

@Getter @Setter - Que podemos ponerlas delante de un atributo o encima de la clase. Si las ponemos encima de la clase, se generan getters y setters para todos los atributos de la clase.

@AllArgsContructor - Genera un constructor con todos los atributos de la clase
@NoArgsContructor - Genera un constructor sin argumentos
@RequiredArgsConstructor - Genera un constructor para los atributos final

@NonNull, que ponemos delante de un argumento de una función:
    1- Si el argumento es null, lanza una excepción de tipo NullPointerException (sin escribir una línea de código con el if y el throw)
    2- Quitando Ambigüedades

@Slf4j - Genera un logger estático en la clase      Depende de la herramienta de logging que tengamos en el proyecto. Si tenemos log4j2, generará un logger de log4j2. Si tenemos logback, generará un logger de logback.
@Log4j2 - Genera un logger estático en la clase

@Builder - Genera un patrón builder para la clase
@SuperBuilder - Genera un patrón builder para la clase que admite herencia

@Data - Genera los métodos getter, setter, equals, hashcode y toString
@Value - Genera los métodos getter, equals, hashcode y toString... pero sin setter, y definiendo las propiedades como final y privadas.

---

# CAGADAS DE JAVA: PARTE I: Setters y getters

Para qué sirven los setters y getters? Sirven para exponer / modificar los atributos PRIVADOS de una clase.


```java 
// El día 1
public class Persona {
    public String nombre;
    public int edad;
}

...
// Del día 2 al 100 tengo montones de personas usando mi clase
Persona p = new Persona();
p.nombre = "Fermín";
p.edad = 45;
System.out.println(p.nombre);
p.nombre = "Menchu";
System.out.println(p.nombre);
```

Pero en JAVA me dicen que esto es una mala PRACTICA: Y ES VERDAD !

¿Por qué? Para facilitar la MANTEBILIDAD de mi código.


```java 
//El día 101
public class Persona {
    public String nombre;
    private int edad;

    public void setEdad(int edad){
        if(edad < 0){
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        this.edad = edad;
    }

    public int getEdad(){
        return this.edad;
    }
}

...

// Este codigo ya no compila
Persona p = new Persona();
p.nombre = "Fermín";
p.edad=45:
p.setEdad(45);
System.out.println(p.getEdad());
```
Y tengo a 500 personas, kalashnikov en mano, en la puerta de mi casa.

Y entonces la "brillante"  XD   solución  de JAVA es: Por si acaso elñ día de mañana quieres meter código asociado a la modificación de un atributo, en lugar de acceder directamente al atributo, crea desde el día 0 un setter y un getter para cada atributo. POR SI ACASO!