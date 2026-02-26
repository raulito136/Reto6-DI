package org.example.reto4ad.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración principal de seguridad de la aplicación web.
 * Define las políticas de autorización, el manejo de sesiones y
 * la redirección a la vista de login.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // En aplicaciones MVC (con vistas HTML), es recomendable dejar CSRF activado.
                // Thymeleaf se encargará de inyectar el token CSRF en los formularios automáticamente.
                .authorizeHttpRequests(auth -> auth
                        // Permitir recursos estáticos (CSS, JS, imágenes)
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        // Rutas públicas: Ver hoteles y buscar (Solo lectura)
                        .requestMatchers(HttpMethod.GET, "/hoteles", "/hoteles/{id}", "/hoteles/busqueda", "/hoteles/calificacion/**", "/hoteles/precio/**", "/hoteles/nombre/**").permitAll()

                        // Cualquier otra acción (crear, editar, borrar, reservar) requiere estar logueado
                        .anyRequest().authenticated()
                )
                // Configuración del login por formulario web clásico
                .formLogin(form -> form
                        .loginPage("/login") // Ruta donde mostraremos nuestro HTML de login
                        .loginProcessingUrl("/login-post") // Ruta interna que procesa el formulario
                        .defaultSuccessUrl("/hoteles", true) // Si el login es correcto, te lleva a los hoteles
                        .failureUrl("/login?error=true") // Si falla, recarga el login con un mensaje de error
                        .permitAll()
                )
                // Configuración del cierre de sesión
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hoteles") // Al salir, te devuelve a la lista pública de hoteles
                        .permitAll()
                );

        return http.build();
    }
}