package com.perfunlandia.gestiondecliente.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.services.ClienteService;
import com.perfunlandia.gestiondecliente.controller.GestionClienteController;
import com.perfunlandia.gestiondecliente.assembler.ClienteModelAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;
import java.util.Optional;

@SpringBootTest // Carga el contexto de la aplicación para pruebas
@AutoConfigureMockMvc // Configura MockMvc para pruebas de controladores
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Permite realizar peticiones HTTP a los controladores

    @MockBean
    private ClienteService clienteService; // Mock del servicio ClienteService

    @MockBean
    private ClienteModelAssembler assembler; // Mock del ensamblador de modelos ClienteModelAssembler

    private Cliente cliente; // Objeto Cliente para pruebas
    private EntityModel<Cliente> clienteModel; // Modelo de entidad para Cliente

    @BeforeEach
    void setUp() { // Configuración antes de cada prueba
        cliente = new Cliente(); // Inicializa un nuevo objeto Cliente
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellido_paterno("Pérez");
        cliente.setApellido_materno("García");
        cliente.setTelefono("123456789");
        cliente.setEmail("juan.perez@email.com");
        clienteModel = EntityModel.of(cliente);
    }

    @Test
    public void testGetAllClientes() throws Exception { // Prueba para obtener todos los clientes
        when(assembler.toModel(cliente)).thenReturn(clienteModel); // Configura el ensamblador para devolver el modelo de cliente
        when(clienteService.listarClientes()).thenReturn(List.of(cliente)); // Configura el servicio para devolver una lista con un cliente

        mockMvc.perform(get("/api/clientes")) // Realiza una petición GET a la ruta /api/clientes
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.clienteList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.clienteList[0].nombre").value("Juan"))
                .andExpect(jsonPath("$._embedded.clienteList[0].email").value("juan.perez@email.com"));
    }

    @Test
    public void testGetClienteById_NotFound() throws Exception {
        when(clienteService.buscarCliente(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/999")) // Realiza una petición GET a la ruta /api/clientes/999
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetClienteById_Found() throws Exception {
        when(assembler.toModel(cliente)).thenReturn(clienteModel);
        when(clienteService.buscarCliente(1)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/1")) // Realiza una petición GET a la ruta /api/clientes/1
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"));
    }
}
