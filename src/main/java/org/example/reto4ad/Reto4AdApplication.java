package org.example.reto4ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Clase principal de configuración y arranque de la aplicación Spring Boot.
 * La anotación SpringBootApplication habilita la autoconfiguración,
 * el escaneo de componentes y la configuración de propiedades.
 */
@SpringBootApplication
@EnableCaching
public class Reto4AdApplication {

	/**
	 * Método principal que inicia la ejecución de la aplicación.
	 * @param args Argumentos de línea de comandos (opcionales).
	 */
	public static void main(String[] args) {
		SpringApplication.run(Reto4AdApplication.class, args);
	}

}