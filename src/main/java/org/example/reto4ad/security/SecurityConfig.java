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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        // Rutas públicas: Ver hoteles y el asistente de IA
                        .requestMatchers(HttpMethod.GET, "/hoteles", "/hoteles/{id}", "/hoteles/busqueda", "/hoteles/calificacion/**", "/hoteles/precio/**", "/hoteles/nombre/**").permitAll()

                        // PERMITIR EL CHAT (Tanto GET como POST para evitar líos)
                        .requestMatchers("/chat/**").permitAll() // <--- AÑADE ESTO

                        .anyRequest().authenticated()
                )
                // OPCIONAL: Si vas a usar POST en el chat desde JS,
                // necesitas ignorar el CSRF solo para esa ruta, o el chat fallará.
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/chat/**") // <--- RECOMENDADO para que el chat funcione sin tokens
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login-post")
                        .defaultSuccessUrl("/hoteles", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/hoteles")
                        .permitAll()
                );

        return http.build();
    }
}