package com.curso.diccionarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Microservicio {
    public static void main(String[] args) {
        SpringApplication.run(Microservicio.class, args); // Inversion of Control. Delego el flujo de la app a Spring
    }
}
