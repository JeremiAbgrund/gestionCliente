package com.perfunlandia.gestiondecliente;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.repository.ClienteRepository;

import net.datafaker.Faker;
@SuppressWarnings("deprecation")
//@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner { // Clase que carga datos de prueba al iniciar la aplicación

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class); // Logger para registrar información

    @Autowired
    private ClienteRepository clienteRepository; // Repositorio para acceder a los datos de Cliente

    public DataLoader() {
        System.out.println("DataLoader constructor llamado");
        // throw new RuntimeException("Forzando error para probar creación del bean");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("==== INICIANDO CARGA DE DATOS FAKER ====");
        Faker faker = new Faker(new Locale("es"));

        // Generar 20 clientes de prueba
        for (int i = 0; i < 20; i++) {
            // Crear un nuevo cliente con datos falsos
            // Faker para generar datos aleatorios
            Cliente cliente = new Cliente();    
            cliente.setNombre(faker.name().firstName());
            cliente.setApellido_paterno(faker.name().lastName());
            cliente.setApellido_materno(faker.name().lastName());
            cliente.setTelefono(faker.phoneNumber().cellPhone());
            cliente.setEmail(faker.internet().emailAddress());
            
            
            try {
                clienteRepository.save(cliente);
                log.info("Cliente creado: {}", cliente.getEmail()); // Registra el cliente creado
            } catch (Exception e) {
                log.info("Error al crear cliente: {} - {}", cliente.getEmail(), e.getMessage()); // Registra el error si ocurre
            }
        }
        log.info("==== FIN DE CARGA DE DATOS FAKER ====");
    }
} 