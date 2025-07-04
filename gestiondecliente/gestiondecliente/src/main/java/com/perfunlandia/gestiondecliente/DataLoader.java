package com.perfunlandia.gestiondecliente;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.repository.ClienteRepository;

import net.datafaker.Faker;
@SuppressWarnings("deprecation")
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public DataLoader() {
        log.info("DataLoader constructor llamado");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("==== INICIANDO CARGA DE DATOS FAKER ====");
        Faker faker = new Faker(new Locale("es"));

        // Generar 20 clientes de prueba
        for (int i = 0; i < 20; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().firstName());
            cliente.setApellido_paterno(faker.name().lastName());
            cliente.setApellido_materno(faker.name().lastName());
            cliente.setTelefono(faker.phoneNumber().cellPhone());
            cliente.setEmail(faker.internet().emailAddress());
            
            try {
                clienteRepository.save(cliente);
                log.info("Cliente creado: {}", cliente.getEmail());
            } catch (Exception e) {
                log.info("Error al crear cliente: {} - {}", cliente.getEmail(), e.getMessage());
            }
        }
        log.info("==== FIN DE CARGA DE DATOS FAKER ====");
    }
} 