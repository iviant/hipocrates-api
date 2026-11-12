
package com.hipocrates.hipocratesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// Indica que esta es la aplicación principal de Spring Boot.
@SpringBootApplication

// Activa el sistema de caché de Spring.
@EnableCaching
public class HipocratesApiApplication {

    public static void main(String[] args) {

        // Inicia nuestra aplicación.
        SpringApplication.run(
                HipocratesApiApplication.class, args
        );
    }
}
