package com.perfunlandia.gestiondecliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // Habilita la gestión de transacciones
public class GestiondeclienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestiondeclienteApplication.class, args); // Inicia la aplicación Spring Boot
    }
}
