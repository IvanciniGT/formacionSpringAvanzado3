package com.curso.diccionarios;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Quiero tener seguridad en mis endpoints

                     // vv  Esto también debe ir en consonancia con lo que vayan a marca a nivel de la app real
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) // Qué pueden usar para configurar la seguridad
                     // @PreAuthorize, @PostAuthorize
                                            // @Secured,
                                                                   // @RolesAllowed (Parte de JEE)
public class ConfiguracionSeguridadAplicacionDePrueba {

    // En automático, al arrancar el módulo de seguridad de Spring (Si está en el classpath y he puesto la anotación: @EnableWebSecurity, Spring lo arrancará)
    // Y ese módulo solicita a Spring todos los SecurityFilterChain que estén configurados.
    // El propio módulo de seguridad de Spring, ya trae un huevo de configuraciones por defecto
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ESTO ES ALGO QUE YO PONGO EN AP DE PRUEBAS EN AUTO.
                .csrf(AbstractHttpConfigurer::disable)  // Utiliza la nueva sintaxis para deshabilitar CSRF
                                                        // Esto va de serie en app STATELESS
                                                        // En apps statefull, cuidado, ya que el navegador guarda la cookie de sesión y la manda con independencia de que sea la app quien hace la petición o sea un atacante
                                                        // Y en ese caso necesitamos CSRF
                // Esto a su vez desactiva la publicación de CORS

                // Estas reglas de aqui abajo, deben ir en consonancia con lo que vayan a poner a nivel global en la app real.
                .authorizeHttpRequests(
                        requests -> requests.anyRequest().permitAll()  // Por defecto, al activar la seguridad, to do está prohibido
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No quiero sesiones
                        // Por defecto, Spring crea una sesión por defecto
                );

        return http.build();
    }

}
