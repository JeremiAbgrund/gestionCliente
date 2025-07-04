package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.services.ClienteService;
import com.perfunlandia.gestiondecliente.controller.GestionClienteController;
import com.perfunlandia.gestiondecliente.assembler.ClienteModelAssembler;
import com.perfunlandia.gestiondecliente.GestiondeclienteApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


@WebMvcTest(GestionClienteController.class)
@ContextConfiguration(classes = GestiondeclienteApplication.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ClienteModelAssembler assembler;

    private Cliente cliente;
    private EntityModel<Cliente> clienteModel;

    @BeforeEach
    void setUp() {
        // Configura un objeto Cliente de ejemplo antes de cada prueba
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido_paterno("Pérez");
        cliente.setApellido_materno("García");
        cliente.setTelefono("123456789");
        cliente.setEmail("juan.perez@email.com");
        
        // Configura el EntityModel para el cliente
        clienteModel = EntityModel.of(cliente);
    }

    @Test
    public void testGetAllClientes() throws Exception {
        // Configura el comportamiento del mock del assembler
        when(assembler.toModel(cliente)).thenReturn(clienteModel);
        
        // Define el comportamiento del mock: cuando se llame a listarClientes(), devuelve una lista con un Cliente
        when(clienteService.listarClientes()).thenReturn(List.of(cliente));

        // Realiza una petición GET a /api/clientes y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$._embedded.clienteList[0].id").value(1)) // Verifica que el primer elemento tenga id 1
                .andExpect(jsonPath("$._embedded.clienteList[0].nombre").value("Juan")) // Verifica que el primer elemento tenga el nombre "Juan"
                .andExpect(jsonPath("$._embedded.clienteList[0].email").value("juan.perez@email.com")); // Verifica que el primer elemento tenga el email correcto
    }

    @Test
    public void testGetClienteById_NotFound() throws Exception {
        // Define el comportamiento del mock: cuando se llame a buscarCliente(999), devuelve Optional.empty()
        when(clienteService.buscarCliente(999)).thenReturn(Optional.empty());

        // Realiza una petición GET a /api/clientes/999 y verifica que la respuesta sea 404 Not Found
        mockMvc.perform(get("/api/clientes/999"))
                .andExpect(status().isNotFound()); // Verifica que el estado de la respuesta sea 404 Not Found
    }

    @Test
    public void testGetClienteById_Found() throws Exception {
        // Configura el comportamiento del mock del assembler
        when(assembler.toModel(cliente)).thenReturn(clienteModel);
        
        // Define el comportamiento del mock: cuando se llame a buscarCliente(1), devuelve el cliente
        when(clienteService.buscarCliente(1)).thenReturn(Optional.of(cliente));

        // Realiza una petición GET a /api/clientes/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Verifica que el cliente tenga id 1
                .andExpect(jsonPath("$.nombre").value("Juan")) // Verifica que el cliente tenga el nombre "Juan"
                .andExpect(jsonPath("$.email").value("juan.perez@email.com")); // Verifica que el cliente tenga el email correcto
    }
} 