// En java 1,8 aparece el paquete java.util.function
// Ese paquete contiene INTERFACES (interface) que permiten referencia a funciones
// Tipos de datos que representan funciones (INTERFACES FUNCIONALES)
// De hecho, mediante la anotación @FunctionalInterface en una interface, puedo crear mis 
// Propias interfaces funcionales.
// Vienen muchas:
// Function<T,R>   Función que recibe un dato de tipo T y devuelve un dato de tipo R
// Consumer<T>     Función que recibe un datos de tipo T y no devuelva nada (void)
//                          setter
// Supplier<R>     Función que no recibe dato y devuelve un dato de tipo R
//                          getter
// Predicate<T>    Función que recibe un dato de tipo T y devuelve un booleano.
//                          is??, has???

// Luego vienen 50 tipos más...

import java.util.function.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class ProgramacionFuncional {
    
    public static void main(String[] args){
        saluda("Ivan");

        Consumer<String> miFuncion = ProgramacionFuncional::saluda; // Y en JAVA 1.8 aparece el operador ::
                                             // ese operador permite referenciar una función
        miFuncion.accept("Menchu");
        imprimirSaludo( ProgramacionFuncional::generarSaludoFormal , "Federico");

        // En java 1.8 aparece un segundo operados. El operador FLECHA ->
        // Operador lambda... nos permite generar expresiones LAMBDA
        // Qué es una expresión lambda? Lo primero de to do UNA EXPRESION
        // Qué es una EXPRESION en un lenguaje de programación?
        String texto = "hola"; // Declaración // Statement = FRASE JAVA
        int numero = 5 + 7;    // Otra frase otro statement
                    ////// Una expresión: Un trozo de código que DEVUELVE UN VALOR

        // Por lo tanto una expresión lambda es un trozo de código que devuelve un valore.. qué valor?
        // Una función anónima declarada dentro de la propia expresión
        Function<String,String> unaFuncion  = ProgramacionFuncional::generarSaludoFormal;
        Function<String,String> otraFuncion = (String nombre) -> { // DECLARAR UNA FUNCION
            return "Estimado "+ nombre;
        };
        Function<String,String> otraFuncion1 = (nombre) -> {
            return "Estimado "+ nombre;
        };
        Function<String,String> otraFuncion2 = nombre -> {
            return "Estimado "+ nombre;
        };
        Function<String,String> otraFuncion3 = nombre -> "Estimado "+ nombre;
        imprimirSaludo( otraFuncion , "Federico");
        imprimirSaludo( otraFuncion1 , "Federico");
        imprimirSaludo( otraFuncion2 , "Federico");
        imprimirSaludo( otraFuncion3 , "Federico");

        imprimirSaludo( nombre -> "Estimado "+ nombre , "Federico");
        imprimirSaludo( ProgramacionFuncional::generarSaludoFormal , "Federico");

        // Y desde JAVA 1.8, que ya tenemos programación funcional, el API entero de JAVA está migrando hacia programación funcional
        // java.util.Stream: Aplicar algoritmos de programación según modelo MAP REDUCE
        // Stream<String>??? Un tipo de Collection muy especial.. Es como un List<String>
        // lo que pasa es que los Stream, vienen preparados para programación funcional (MAP-REDUCE)
        // Puedo pasar de un List (Map, Set) -> Stream mediante la funcion .stream()
        // Stream .map que me permite generar una Stream nueva cargada con el resultado de aplcicar una función de mapeo sobre los elementos del Stream original
        // Puedo pasar de un Stream a un List (Map, Set) ... mediante la fucnión .collect(COLLECTOR) // Desde Java 11 .toList();

        List<Integer> numeros = List.of(1,2,3,4,5,6); // JAVA 9

        List<Integer> dobles = new ArrayList<>();
        for(int num : numeros){
            dobles.add(num * 2);
        }

        List<Integer> dobles2 = numeros.stream().map( num-> num*2).toList(); // Java 11
        List<Integer> dobles3 = numeros.stream().map( num-> num*2).collect(Collectors.toList()); // Java 8
        System.out.println(dobles2);
    }

    public static void saluda(String nombre){
        System.out.println("Hola "+ nombre);
    }

    public static String generarSaludoFormal(String nombre){ // DECLARAR UNA FUNCION
        return "Hola "+ nombre;
    }

    public static void imprimirSaludo( Function<String,String> funcionGeneradoraDeSaludos, String nombre){
        System.out.println(funcionGeneradoraDeSaludos.apply(nombre));
    }

}
