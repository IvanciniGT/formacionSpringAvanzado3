package com.curso.diccionarios;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity // Quiero tener seguridad en mis endpoints
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // Qué pueden usar para configurar la seguridad
                     // @PreAuthorize, @PostAuthorize
                                            // @Secured,
                                                                   // @RolesAllowed (Parte de JEE)
public class AplicacionDePrueba {
}
