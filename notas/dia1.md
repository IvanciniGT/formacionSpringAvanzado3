# Paradigma de programación

La forma en la que uso un lenguaje para transmitir mis objetivos, pensamientos.. lo que sea que quiero transmitir. Esto no es algo que tengamos solo en los lenguajes de programación. También lo tenemos en el lenguaje natural:

>> Felipe, pon una silla debajo de la ventana.                  IMPERATIVO

>> Felipe, debajo de la ventana hay que poner una silla.        DECLARATIVO


>> Felipe, pon una silla debajo de la ventana
    -> ERROR: NoSpaceAvailableUnderWindowException
Y aqui sale la mierda (el guarreo) del lenguaje imperativo:

Felipe: Si hay algo debajo de la ventana que no sea una silla,      CONDICIONAL
    quítalo y pon una silla debajo de la ventana.                   IMPERATIVO (ORDEN)
Felipe: Si no hay una silla debajo de la ventana,                   CONDICIONAL
    Felipe: SI NO hay sillas:                                       CONDICIONAL
            Vete al IKEA y compra una silla.                        ORDEN
    pon una silla debajo de la ventana.                             IMPERATIVO (ORDEN)

Odioamos el lenguaje imperativo (otra cosa es que estamos muy acostumbrados a él). El problema es que el lenguaje imperativo me distrae de mi objetivo, pasando a centrarme en cómo conseguir ese objetivo.

La gracia del lenguaje declarativo es que me permite centrarme en el objetivo, no en cómo conseguirlo.
DELEGO LA RESPONSABILIDAD DE CONSEGUIR EL OBJETIVO EN FELIPE.

Casi todas las herramientas y frameworks que lo están petando a día de hoy lo hacen por hablar en un lenguaje declarativo:
- Kubernetes/OpenShift
- Docker(docker-compose)
- Terraform
- Ansible
- Angular
- Spring


Y nos cuesta cambiar la mentalidad... a usar lenguajes declarativos.
---

El software es un producto MANTENIBLE!!!! Como un coche!

    ESCRIBO CODIGO <> Pruebas -> OK  -> REFACTORIZACION <> Pruebas -> OK
    <-----------50% del trabajo----->   <-----------50% del trabajo----->

Y cuando digo 50% del trabajo, significa que si hacer que el programa funcione me lleva 8 horas, voy a dedicar otras 8 horas a refactorizarlo.

# Principios SOLID de desarrollo de software

Son 5 principios, recopilados por el TIO BOB (Robert C. Martin) en 2000, que buscan mejorar la calidad del software:
- Facilitar el mantenimiento.
- Facilitar la evolución.
- Facilitar la reutilización.
- Facilitar las pruebas.

La recopilación del tio BOB es un puto desastre.

S - Single Responsability Principle - Principio de responsabilidad única.
    Empezó como:   Una clase debe atender a una única responsabilidad.
    Más adelante:  Una clase debe tener una única razón para cambiar.
    Finalmente:    una clase debe atender a un único actor. ** BUENA !!!!!
                        Usuario de búsqueda
                        Editor de diccionarios
                        Administrador de diccionarios (importar/exportar)
                        Administradores de BBDD (Oracle -> Postgres)
O - Open/Closed Principle - Principio de abierto/cerrado.
L - Liskov Substitution Principle - Principio de sustitución de Liskov.
    Una subclase debe poder ser sustituida por su clase base sin que el programa falle.
    Esto explicado en plata : El contrato debe estar MUY CLARO:
        - una subclase no pode poner restricciones adicionales a los argumentos de los métodos de la clase base.
        - una subclase no puede lanzar excepciones que no lance la clase base.
        - una subclase no puede devolver valores más laxos de los que devuelve la clase base.
I - Interface Segregation Principle - Principio de segregación de interfaces.
    Mejor muchas interfaces cliente-específicas que una general.
    No des a alguien que necesita hacer un tipo de operaciones una interfaz con operaciones que no necesita.
    CUIDADO !!!! No pongas muchos métodos en una interfaz que luego tengas la obligación de implementar en todas las clases que implementen esa interfaz (que quizás no necesiten esos métodos).
D - Dependency Inversion Principle - Principio de inversión de dependencias.

Los principios los tengo, yo como persona. Y cuando actúo puedo respetarlos o no. Y si no los respeto no pasa nada.

Estos son algunos... Otros son KISS, DRY, YAGNI, SOC, etc.

### SoC - Separation of Concerns - Separación de preocupaciones

Originalmente esto significa que al enfrentarme a un desarrollo, debo pensar en las cosas por separado (yo como humano): Persistencia / Lógica de negocio / Presentación.

Más adelante se empezó a variar su significado: Separar en clases distintas las distintas responsabilidades/preocupaciones de un sistema.
    - Clase PERSISTENCIA
    - Clase LÓGICA DE NEGOCIO
    - Clase PRESENTACIÓN

---

## D - Dependency Inversion Principle - Principio de inversión de dependencias.

Este principio dice que los módulos de alto nivel no deben depender de implementaciones (código, clases) de los módulos de bajo nivel. Ambos deben depender de abstracciones. Además, las abstracciones no deben depender de implementaciones.

### Patrones que me ayudan a cumplir con el DIP

Hay patrones muy estudiados y aceptados que me ayudan a cumplir con el DIP. Algunos de ellos son:
- Factory Method.
- Inyección de dependencias.

#### Inyección de dependencias

Es un patrón que dice que una clase no debe crear instancias (new) de los objetos que necesita para funcionar. En lugar de eso, le deben ser suministrados.

---

# Inversión de control?

Un patrón por el cuál delego el flujo de mi aplicación a un framework. En lugar de ser yo el que controle (escribe, codifica) el flujo de mi aplicación, delego esa responsabilidad a un framework.

De hecho, la función main de cualquier aplicación SpringBoot tiene solo 1 línea de código:
```java
public class Aplicacion {
    public static void main(String[] args) {
        SpringApplication.run(Aplicacion.class, args); // Inversión de control
    }
}
```

Claro.. si vosotros fuerais Spring, qué diríais llegados a este punto? Y QUE COJONES HACE TU APLICACION ?
Y se lo tendremos que explicar.. usando lenguaje DECLARATIVO...

## Proceso de carga de palabras en el diccionario (en una BBDD.,.. desde un fichero). REQUISITOS = LENGUAJE DECLARATIVO

- Quiero leer un fichero con un determinado formato.
- Ah... y quiero que se mande un correo cuando el programa arranca.
- Ah... y quiero que se mande un correo cuando el programa termina.
- Ah... y quiero que a leer cada linea del fichero (una palabra) se transforme la palabra a minúsculas.
- Ah.. y quiero que se guarde en la BBDD
- Ah... y quiero que si una palabra viene sin significados, se guarde en un fichero de log.
- Ah.. y si hay palabras sin significados, al acaba quiero que se mande un correo con el fichero de log.

    ||
    vv

```pseudolanguage

1. Manda un correo
2. Abre el fichero
2.5: Abre fichero de log
2.6: Abre la BBDD
3. Para cada linea del fichero                      (BUCLE)
    3.1 Transforma la palabra a minúsculas
    3.2 Mira si tiene significados                  (CONDICIONAL)
        - SI: Guarda la palabra en la BBDD
        - NO: Guarda la palabra en el fichero de log
4. Cierra el fichero
4.5: Cierra el fichero de log
4.6. Cierra la BBDD
5. Manda un correo de fin
6. IF: Hay palabras sin significado
    6.1 Manda un correo con el fichero de log

```

QUE ACABO DE DEFINIR? el flujo de mi aplicación. Usando que paradigma de programación? IMPERATIVO.

Spring pone el flujo de una aplicación. Y una de las primeras cosas a aprender es el flujo que sigue Spring a la hora de ejecutar una aplicación.

# Spring

Un framework para el desarrollo de Programas JAVA (OJO: Y KOTLIN!!!) que ofrece INVERSION DE CONTROL!!!

El framework más aberrante (por grande, extenso) que existe en el mundo del desarrollo de software (+200 librerías .jar) -solo comparable con .net core- se ha creado para facilitar el uso de un patrón de inyección de dependencias (mediante el uso de un patrón de inversión de control), para poder respetar el ppo de la inversión de dependencias, y así tener un sistema más mantenible, evolutivo, reutilizable y testeable, con componentes DESACOPLADOS.

## Framework vs librería

La libreria es algo que invoque yo y me da un resultado.. que uso.

Un framework, además de librerías, me impone una forma de usarlas (metodología). Y eso estandariza el desarrollo de software.

# JUNIT también es un framework
# Cucumber también es un framework

---

# Contexto

Las herramientas, los lenguajes, los frameworks, las librerías, las metodologías de gestión, los patrones de diseño, los principios de desarrollo, las arquitecturas, las metodologías de desarrollo evolucionan en paralelo, para resolver los problemas que tenemos en un momento dado.

Hace 20 años, teníamos unos problemas.. hoy tenemos otros muy distintos.

Lenguaje: JAVA
Herramienta: Maven, git
Framework: Spring
Metodología de gestión: Scrum
Patrones de diseño: Inyección de dependencias, decorador
Arquitectura: Microservicios / Clean Architecture / Hexagonal / Onion
Metodología de desarrollo: TDD, BDD

Y todas esas cosas encajan entre si. Y si saco una pieza de la ecuación, la ecuación no funciona.
Esto no va de aprender Spring... hay que tener una vista global de todo.


---

# PRUEBAS:

## Vocabulario en el mundo del testing

- Causa Raíz    El motivo por el que un humano comete un error.
- Error         Los humanos cometemos errores (ya se sabe que errar es de humanos). Las máquinas cometen errores? NO
                Los humanos podemos cometer un error por estar distraídos, cansados, falta de conocimiento. 
- Defecto       Al cometer un error, podemos introducir un defecto en un producto.
- Fallo         Ese defecto puede manifestarse como un fallo al usar el producto.

## Para qué sirven las pruebas?

- Para asegurar el cumplimiento de unos requisitos.
- Para saber qué tal va el proyecto ¿?
- Para tratar de identificar la mayor cantidad posible de defectos antes del paso a producción.
- En ocasiones lo que haré no será buscar defectos... sino tratar de provocar FALLOS... para posteriormente identificar el defecto que lo origina... que es lo que quiero arreglar. El proceso de identificar un defecto y arreglarlo a partir del fallo que lo provoca es lo que llamamos DEPURAR (debugging).
- Para recopilar en caso de fallo toda la información que pueda para una rápida identificación del defecto.
- Haciendo un análisis de causas raíces para tratar de tomar acciones preventivas que eviten nuevos ERROR>DEFECTO>FALLO.
- Para aprender del sistema... y usar esa información en futuros proyectos

## Tipos de pruebas conocéis?

Cualquier prueba se centra en una única característica del producto. Por qué? 
    Porque así, si falla, rápidamente sé qué parte del producto falla.
Las pruebas se clasifican en varias taxonomías paralelas:

### Según el objeto de prueba

-  Pruebas funcionales      Se centran en requisitos funcionales
-  Pruebas no funcionales   Se centran en requisitos no funcionales
   -  Estrés
   -  Carga
   -  Rendimiento
   -  UX
   -  Seguridad
   -  HA

### Según su ambito de aplicación (SCOPE)

- Fabrico Bicicletas:
    Ruedas
    Sistema de frenos
    Cuadro
    Dinamo + batería + luces
    Sillín

- unitarias               Se centran en una característica (igual que cualquier otro tipo de prueba) de un componente AISLADO

        SILLIN:     Podría soldar 4 hierros "malsoldaos" (bastidor), atornillar allí el sillín, y así lo aíslo del resto de componentes.
                    Lo ato al bastidor.. pero coño confío en el bastidor. EL BASTIDOR SE QUE NO FALLA

                        - Prueba unitaria UX: Es cómoda?
                        - Prueba de estrés? Al mes usándolo, el cuero aguanta?
                        - Prueba de carga? si subo un tio de 100Kgs... aguanta o se desmonta?
                        - Prueba de seguridad? En las curvas, me aguanta.. o me resbalo?

        Sistema de frenos? Podría soldar 4 hierros "malsoldaos" (bastidor), coloco allí el sistema de frenos... y en medio de las pinzas pongo un sensor de presión.
                        - Aprieto la palanca (ejecutar apretarPalanca())
                        - Y reviso que:
                          - 1. Que las pinzas de freno se cierren
                          - 2. Además que lo hagan con la suficiente fuerza

    Pregunta: Si se pasan todas estas pruebas, tengo garantía de que la bici va a funcionar bien? NO

    Qué gano entonces? CONFIANZA +1
        Voy bien... voy dando pasos en firme.

- integración           Se centra en la COMUNICACION entre !! 2 !! componentes

    Sistema de frenos + Rueda: Podría soldar 4 hierros "malsoldaos" (bastidor), coloco allí el sistema de frenos... y en medio de las pinzas una rueda.
                        - Pongo la rueda a girar
                        - Aprieto la palanca (ejecutar apretarPalanca())
                        - Y reviso que:
                          - 1. Que la rueda se para
                        Y mira que no tu! Resulta que las piunzas cierran.. pero no lo suficiente para el grosor de llanta que tenemos!
                        UPS! Tengo un problema con las ruedas? NO! Tengo un problema con el sistema de frenos? NO
                        Tengo un problema de integración entre el sistema de frenos y las ruedas.
                        El sistema de frenos no es capaz de COMUNICARSE (pasar la energía de rozamiento) con la rueda!

    Pregunta: Si se pasan todas estas pruebas, tengo garantía de que la bici va a funcionar bien? NO

    Qué gano entonces? CONFIANZA +1
        Voy bien... voy dando pasos en firme.

- sistema (end-to-end)  Se centran en el comportamiento del sistema en su conjunto:

    Cojo la bici, y me hago 200kms con ella: GRANADA - ALMERIA, ida y vuelta.
    Y llego bien. La bici ha respondido bien... Estoy descansado... no ome duele nada!

    Si se pasan estas pruebas puedo ya entregar a mi clientes? SI!

    Pregunta: Si se pasan todas estas pruebas, necesito hacer pruebas unitarias y de integración? NO me hace falta.
    YA TENGO UNA BICI QUE FUNCIONA ADECUADAMENTE

    Donde está el truco?
    - Y si no pasan? Dónde está el problema? NPI
    - Cuándo puedo hacer estas pruebas? cuando tengo bici! Y hasta entonces voy a ciegas? Poniendo perejil a San Pancracio para que nada falle?

  - aceptación

### Según el conocimiento del producto
- Caja negra            Cuando no conozco el funcionamiento interno del producto o no lo tengo en cuenta.
- Caja blanca           Cuando conozco el funcionamiento interno del producto y lo tengo en cuenta.

### Según la forma de ejecución

- Dinámicas: las que necesitan poner en marcha el sistema/producto (usarlo)
- Estáticas: las que no necesitan poner en marcha el sistema/producto (revisar)

### Otras clasificaciones

- Regresión: cuando repito pruebas que ya he hecho para asegurarme de que no he introducido nuevos defectos.
- Pruebas de humo: pruebas básicas que se hacen para asegurarse de que el sistema se ha instalado correctamente.

Algunos tipos de pruebas (especialmente los que más nos interesan) SON IMPOSIBLES DE HACER POR DEFINICION... sin usar ciertas librarías y respetar ciertos principios como el principio de inversión de dependencias... y usando algo como un patrón de inyección de dependencias.

## Definiendo un test

Para definir una prueba usamos 3 partes: 
- definir el contexto de ejecución de la prueba     DADO            GIVEN
- ejecutar lo que queremos probar                   CUANDO          WHEN
- comprobar que el resultado es el esperado         ENTONCES        THEN

Además, he de cumplir con los principios de desarrollo de pruebas: FIRST

F - Fast
I - Independent
R - Repeatable
S - Self-validating
T - Timely              Oportuna



---


```java
public class Rectangulo {

    private int base;
    private int altura;

    public Rectangulo() {
    }

    public Rectangulo(int base, int altura) {
        this.base = base;
        this.altura = altura;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getBase() {
        return base;
    }

    public int getAltura() {
        return altura;
    }

    public int getArea() {
        return base * altura;
    }
}

public class Cuadrado extends Rectangulo {

    public Cuadrado(){
        super();
    }

    public Cuadrado(int lado) {
        super(lado, lado);
    }

    public void setBase(int base) {
        setAltura(base);
    }

    public void setAltura(int altura) {
        setBase(altura);
    }
}

// Y ESTE EJEMPLO ES LA MAYOR MIERDA DEL MUNDO... Por que? ROMPE CON EL PRINCIPIO DE SUSTITUCION DE LISKOV (Bárbara Liskov)

@Test
public void verificarAreaDelRectangulo(Rectangulo r) {
    Rectangulo rectangulo = new Cuadrado(3);
    assertEquals(9, rectangulo.getArea());
}
@Test
public void verificarAreaDelRectangulo(Rectangulo r) {
    Rectangulo rectangulo = new Rectangulo(3,3);
    assertEquals(9, rectangulo.getArea());
}

```


CuadradoNoEditable si es un RectanguloNoEditable
RectanguloNoEditable

```java


public class RectanguloNoEditable {

    private int base;
    private int altura;

    public RectanguloNoEditable(int base, int altura) {
        this.base = base;
        this.altura = altura;
    }

    public int getBase() {
        return base;
    }

    public int getAltura() {
        return altura;
    }

    public int getArea() {
        return base * altura;
    }
}

public class CuadradoNoEditable extends Rectangulo {

    public CuadradoNoEditable(int lado{
        super(lado,lado);
    }

}
