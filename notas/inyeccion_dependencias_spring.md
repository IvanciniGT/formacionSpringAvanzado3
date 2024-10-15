# Cómo pedir a Spring objetos que necesito?

2 Métodos:

## Con la anotación @Autowired 

DEBO LIMITAR SU USO AL EXTREMOS. En la mayor parte de los casos es una MUY MUY MUY MUY MALA PRACTICA.

```java

public interface MiInterfaz {
    public void hacerAlgo();
}

import org.springframework.beans.factory.annotation.Autowired;

public class MiClase {

    @Autowired
    private MiInterfaz miInterfaz;

    public MiClase(){
        // Aqui no puedo usar aún miInterfaz... Spring no le ha puesto valor.
    }

    public void otraCosaQueHace(){
        // Aqui necesito un objeto de tipo: miInterfaz
    }
    public void otraCosaMasQueHace(){
        // Aqui necesito un objeto de tipo: miInterfaz
    }
}
```

Con esa anotación le indico a Spring que necesito un objeto de tipo MiInterfaz... QUE SE ENCARGUE DE SUMINISTRARLO. De hecho... Le estoy diciendo que se encargue de ello? IMPERATIVO
Nosotros con la anotación Autowired estamos usando lenguaje DECLARATIVO: Este objeto debe cargarse en automatico.

Limitaciones:
 - Esto solo funciona si Spring es quién crea la instancia de MiClase. Si soy yo quien crea la instancia NO FUNCIONA.
  ```java
    MiClase miClase = new MiClase(); // Si esto lo hago yo... la variable miInterfaz va a estar a null toda su puñetera vida. Spring no le pone valor.. No se encarga de ello.
    ```
    Caso contrario es que lo haga Spring:
    ```java
    MiClase miClase = new MiClase(); // Si esto lo hace Spring... la variable miInterfaz va a tener un valor. Spring se encarga de ello.
    miClase.miInterfaz = // LO QUE SEA que hayamos configurado en Spring para esto; (1)
    ```
 - Cuándo se establece la variable? Después de que se ejecute el constructor... por lo tanto, si necesito la variable en el constructor, la tengo rellena? NO.

Notas:
- Realmente Spring hace lo que os he puedo ahí arriba? (1)
  Ese es el código real que escribirá y ejecutará Spring?
  Ese código compilaría en JAVA? NO PORQUE LA VARIABLE ES PRIVADA !!!!!!
    private MiInterfaz miInterfaz;
    miClase.miInterfaz =
  Entonces??? Spring lo hace usando un paquete de java llamado Reflection. Reflection es un paquete de java (disponible desde JAVA 1.1) que permite atacar directamente a la memoria de la JVM, saltándose las restricciones de acceso de JAVA. Es decir, puedo acceder a variables privadas, métodos privados, etc. Es una puerta trasera GIGANTE. De hecho en las últimas versiones de JAVA se ha intentado limitar su uso (se ha desactivado por defecto). Está considerado INSEGURO, y además va lento.
  CONSECUENCIA = NO USAR!!!! REPITO: NO USAR NUNCA JAMAS !!!!
  Y si no me queda más alternativa LO USO.
    Reglas del club de la lucha de Spring:
        1. No usar Autowired
        2. NO USAR Autowired
        3. NO USAR Autowired
        4. Solo si no me queda más remedio, usar Autowired
  Antes no había opción.. ahora si. Luego la vemos!

## Directamente solicitar una variable como argumento en aquellos métodos que la necesiten.

```java

public interface MiInterfaz {
    public void hacerAlgo();
}

public class MiClase {

    public void otraCosaQueHace(MiInterfaz miInterfaz){ // PUNTO PELOTA
        // Aqui necesito un objeto de tipo: miInterfaz
    }
}
```
Aquí no hay que poner NADA DE NADA !!!!

Limitaciones:
- Esto solo funciona si SPRING es quien llama a la función (quién la ejecuta). Si soy yo quien la ejecuta, me toca a mi pasar ese dato.

Notas:
- ESTO ES GUAY... ES LO QUE HAY QUE USAR !

... Y qué pasa en un ejemplo con el que habías puesto arriba, donde tengo una clase que necesita un objeto en varias funciones? Tengo que pedirlo en todas?


```java

public interface MiInterfaz {
    public void hacerAlgo();
}

public class MiClase {

    private final MiInterfaz miInterfaz;

    public MiClase(MiInterfaz miInterfaz){
        this.miInterfaz = miInterfaz;
        // Aqui SI puedo usar miInterfaz... Me la han pasado!
    }

    public void otraCosaQueHace(){
        // Aqui necesito un objeto de tipo: miInterfaz
    }
    public void otraCosaMasQueHace(){
        // Aqui necesito un objeto de tipo: miInterfaz
    }
}
```
Y al fin y al cabo el constructor no es sino una función más... y si necesito el objeto en el constructor, me lo pasan en el constructor.

Limitaciones:
- Esto solo funcionará si Spring es quien crea la instancia de MiClase. Si soy yo quien la crea, me toca a mi pasarle el objeto.
  Pero ahora el código que escribe Spring será:
  ```java
    MiClase miClase = new MiClase(miInterfaz); 
  ```

OTRO TEMA SERÁ: Cómo le digo a Spring que cree una instancia de mi clase o que llame a una función mía... PERO DE ESO YA HABLAREMOS.

---

# Cómo decirle a Spring qué debe entregar (suministrar) cuando alguien le pida un objeto de un determinado tipo?

2 opciones:

## Opción 1: Anotando una clase que implementa la interfaz que se solicita con @Component

```java

package com.curso;
public interface MiInterfaz {
    public void hacerAlgo();
}
/////
import com.curso.MiInterfaz;  // ESTO ES LA DEPENDENCIA
public class MiClase {

    private final MiInterfaz miInterfaz;

    public MiClase(MiInterfaz miInterfaz){
        this.miInterfaz = miInterfaz;
        // Aqui SI puedo usar miInterfaz... Me la han pasado!
    }
    public void otraCosaQueHace(){
        // Aqui necesito un objeto de tipo: miInterfaz
    }
}
/////
import org.springframework.stereotype.Component;
import com.curso.MiInterfaz; // ESTO ES LA DEPENDENCIA
@Component
public class MiClaseQueImplementaInterfaz implements MiInterfaz {
    public void hacerAlgo(){
        System.out.println("Haciendo algo");
    }
}
```

Si anoto una clase como @Component, le estoy diciendo a Spring que cuando alguien le pida un objeto de un determinado tipo (~~MiClaseQueImplementaInterfaz |~~ MiInterfaz), le entregue una instancia de esa clase.

Spring ofrece un montón de anotaciones variantes (que extienden) de @Component:
Algunas de ellas solo aportan valor SEMANTICO (le ayudan a un desarrollador que entrase a ver el código a entender cuál es el objetivo de la clase):
- @Service - Esta clase contiene lógica de negocio
- @Repository - Esta clase contiene lógica de acceso a datos
- @Controller - Esta clase contiene lógica de control de la aplicación
Además, Spring tiene anotaciones que aportan valor FUNCIONAL y no solo semántico:
- @RestController - Esta clase contiene lógica de exposición de un servicio... exposición tipo REST... Y por ende necesita ir montada en un servidor de aplicaciones... y además quiero poder vincular sus funciones a ENDPOINTS HTTP.

Notas:
- Por defecto, Spring creará una única instancia de la clase que se anota con @Component, y siempre entregará esa instancia a cualquier clase que la solicite. Es decir, Spring ofrece por defecto un comportamiento tipo Singleton... Solo por poner @Component. OJO.. no es un singleton real... Si yo quiero puedo crear otra instancia de esa clase: new MiClaseQueImplementaInterfaz()... simplemente tengo garantía de 1que Spring no lo hará.
- Ese comportamiento por defecto lo puedo cambiar.
  - Si quiero que cada vez que alguien pida un objeto del tipo MiInterfaz, Spring le entregue una nueva instancia de MiClaseQueImplementaInterfaz, debo anotar la clase ADICIONALMENTE con @Scope("prototype"). El valor por defecto es @Scope("singleton").
  - Si por ejemplo, en una aplicación web, quiero que Spring cree un objeto y que siempre entregue elñ mismo a cualquiera que solicita uno en el mismo HTTP Request que ha hecho ESE usuario (hilo de ejecución) , entonces debo anotar la clase con @Scope("request").


NOTA ENORME: SI CON ESTA OPCIÓN ME VALE... DEJAR DE LEER ESTA CHULETA !
Cuándo no me valdría? Esta opción se basa en PONER la anotación @Component en la clase que implementa la interfaz que necesito.
Y si esa clase no es mía? es de una librería de terceros? Puedo ir a su código y poner encima de la clase esa anotación? NO.

## Opción 2: Uso de Anotación @Bean y @Configuration

```java
// Y Mira por donde, esas clases/interfaces no son mias
package com.libreria.de.terceros;
public interface SuInterfaz {
    public void hacerAlgo();
}

package com.libreria.de.terceros;
import com.libreria.de.terceros.SuInterfaz; 
public class SuClaseQueImplementaInterfaz implements SuInterfaz {
    public void hacerAlgo(){
        System.out.println("Haciendo algo");
    }
}
/////
import com.libreria.de.terceros.SuInterfaz;  // ESTO ES LA DEPENDENCIA
public class MiClase {

    private final SuInterfaz suInterfaz;

    public MiClase(SuInterfaz suInterfaz){
        this.suInterfaz = suInterfaz;
        // Aqui SI puedo usar suInterfaz... Me la han pasado!
    }
    public void otraCosaQueHace(){
        // Aqui necesito un objeto de tipo: suInterfaz
    }
}
/////
@Configuration // Oye, Spring, en esta clase, tengo funciones anotadas con @Bean... QUE LO SEPAS !!!!
public class MiConfiguracion{
    @Bean // Oye, si alguien te pide un objeto de tipo SuInterfaz, dale lo que devuelve esta función.
    //@Scope("prototype") 
    public SuInterfaz federico(){ // La podeis llamar como os de la real gana!
        return new SuClaseQueImplementaInterfaz();
    }
}
```

Cuando alguien pida un objeto de tipo SuInterfaz, Spring llamará a la función federico() de la clase MiConfiguracion, y entregará lo que devuelva esa función.

Notas:
- Por defecto, Spring solo llamará a esa función 1 única vez.. y cacheará su resultado.. y devolverá (suministrará) siempre ese mismo objeto cuando alguien pida un objeto de tipo SuInterfaz. Es decir, por defecto, Spring ofrece un comportamiento tipo Singleton.
- - Comportamiento que puedo cambiar, igual que al usar la anotación @Component, mediante el uso de la anotación @Scope("prototype") o @Scope("request").

---

Tanto la opción 1, como la opción 2 solo funcionarán si:
- Spring, al arrancar lee las clases anotadas con @Component y/o @Configuration. Si no las lee, nada de esto funciona.

Cómo le pido a Spring que lea estas clases?
- @ComponentScan("com.curso") - Le estoy diciendo a Spring que lea todas las clases que existan en el CLASSPATH que estén en ese paquete y sus subpaquete en busca de anotaciones @Component y @Configuration.
- Hoy en día es incluso un poco más fácil. podemos usar una anotación llamada @SpringBootApplication que hace lo mismo que @ComponentScan("com.curso") y además hace otras cosas. Cuando uso esta anotación, no necesito suministrarle el paquete base, ya que por defecto lo toma del paquete donde está la clase que tiene la anotación.

```java
package com.curso;
@SpringBootApplication
public class Aplicacion {
}
```

En automático, si pido a Spring que arranque esta aplicación, leerá todas las clases que estén en el paquete com.curso y sus subpaquetes en busca de clases con anotaciones @Component y @Configuration.

---

¿Qué ocurre si tengo varias clases que implementan una interfaz marcadas con @Component? Depende:

- A priori no tiene por qué pasar nada. Puede ser que alguien, pida un List<MiInterfaz> y Spring le entregue una lista con todas las instancias de las clases que implementan MiInterfaz y que estén marcadas con @Component. GUAY !
- El problema es que tengo una clase que pida no una lista.. sino solo UNA. Y en ese caso, Spring no sabe a priori cuál de las clases que implementan la interfaz debe entregar. En ese caso, Spring se detiene en el arranque.. Ni arranca. Falla con una excepción.
    Para resolver esto, podemos usar varias estrategias:
    - @Primary - Puedo poner esta anotación en una clase que tenga también la anotación @Component. Le estoy diciendo a Spring que si tiene que elegir entre varias clases que implementan la misma interfaz, elija esta.
      Si al final solo quiero usar una... para que tengo otras 7? Esto prácticamente solo tiene un escenario de uso: PRUEBAS: Fake
    - Pedir que se usen implementaciones solo de un determinado paquete. @ComponentScan("com.curso")
        En la clase que pide MiInterfaz:
        ```java

        @ComponentScan("com.curso")
        public class MiClase {

            private final MiInterfaz miInterfaz;

            public MiClase(MiInterfaz miInterfaz){
                this.miInterfaz = miInterfaz;
                // Aqui SI puedo usar miInterfaz... Me la han pasado!
            }
            public void otraCosaQueHace(){
                // Aqui necesito un objeto de tipo: miInterfaz
            }
        }
        ```

    - @Qualifier("etiqueta") - Puedo poner esta anotación tanto en la clase que pide la interfaz, como en la que la que la implement y tiene @Component. Le estoy diciendo a Spring que si tiene que elegir entre varias clases que implementan la misma interfaz, elija la que tenga la etiqueta que le paso como parámetro.
      
    ```java
    public interface MiInterfaz {
        public void hacerAlgo();
    }

    @Component
    @Qualifier("federico")
    public class MiClaseQueImplementaInterfaz implements MiInterfaz {
        public void hacerAlgo(){
            System.out.println("Haciendo algo");
        }
    }

    @Component
    @Qualifier("pepe")
    public class MiClaseQueImplementaInterfaz3 implements MiInterfaz {
        public void hacerAlgo(){
            System.out.println("Haciendo algo");
        }
    }

    public class MiClase {

        private final MiInterfaz miInterfaz;

        public MiClase(@Qualifier("pepe") MiInterfaz miInterfaz){
            this.miInterfaz = miInterfaz;
            // Aqui SI puedo usar miInterfaz... Me la han pasado!
        }
        public void otraCosaQueHace(){
            // Aqui necesito un objeto de tipo: miInterfaz
        }
    }

    public class OtraClase {

        private final MiInterfaz miInterfaz;

        public OtraClase(@Qualifier("federico") MiInterfaz miInterfaz){
            this.miInterfaz = miInterfaz;
            // Aqui SI puedo usar miInterfaz... Me la han pasado!
        }
        public void otraCosaQueHace(){
            // Aqui necesito un objeto de tipo: miInterfaz
        }
    }
    ```

Y HEMOS ACABADO !

---

# Patrón Singleton

Esto me garantiza que de esa clase SOLO PUEDE EXISTIR UNA UNICA INSTANCIA.

```java
public class MiClaseSingleton {
    private static volatile MiClaseSingleton instancia = null; // volatile: JAVA, esta variable puede ser modificada por varios hilos en paralelo, tengo un sistema que trabaja con muchos hilos (concurrente)... NO cachees la variable a nivel del core de la CPU (las CPUS tienen cache)... Puede ser que hayan tocado la variable desde otro hilo (y por tanto en otro CORE de la CPU) y que leas un valor inadecuado si lo haces.
    private MiClaseSingleton(){
        // Aqui puedo hacer cosas
    }
    public static MiClaseSingleton getInstancia(){
        if(instancia == null){                        // Evitar el bloqueo (que computacionalmente es muy caro) salvo que sea necesario.
                                                      // La primera vez.
            synchronized(MiClaseSingleton.class){     // Evitar condiciones de carrera (2 hilos simultáneos entrando a la vez al if)
                if(instancia == null){                // Que el constructor se invoque solo si la variable esta a null.
                  instancia = new MiClaseSingleton();
                }
            }
        }
        return instancia;
    }
}
```