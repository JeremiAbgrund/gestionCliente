package com.perfunlandia.gestiondecliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.perfunlandia.gestiondecliente.assembler.ClienteModelAssembler;
import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class GestionClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    // Obtener todos los clientes
    @GetMapping
    public CollectionModel<EntityModel<Cliente>> listarClientes() {
        List<EntityModel<Cliente>> modelos = clienteService.listarClientes().stream()
            .map(assembler::toModel)
            .toList();
        return CollectionModel.of(
            modelos,
            linkTo(methodOn(GestionClienteController.class).listarClientes()).withSelfRel(),
            linkTo(methodOn(GestionClienteController.class).agregarCliente(null)).withRel("crear") 
        );
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public EntityModel<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        Cliente cliente = clienteService.buscarCliente(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return assembler.toModel(cliente);
    }

    // Agregar nuevo cliente
    @PostMapping
    public ResponseEntity<String> agregarCliente(@RequestBody Cliente clienteNuevo) {
        try {
            String resultado = clienteService.agregarCliente(clienteNuevo);
            // 200 OK: Si se agrega correctamente, devuelve 200 con mensaje
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            // 500 Internal Server Error: Si ocurre un error, devuelve 500 con mensaje de error
            return ResponseEntity.status(500).body("Error al agregar el cliente: " + e.getMessage());
        }
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        String resultado = clienteService.actualizarCliente(id, clienteActualizado);
        if (resultado.equals("Cliente no encontrado")) {
            // 404 Not Found: Si el cliente no existe, devuelve 404
            return ResponseEntity.notFound().build();
        }
        // 200 OK: Si se actualiza correctamente, devuelve 200 con mensaje
        return ResponseEntity.ok(resultado);
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable int id) {
        String resultado = clienteService.eliminarCliente(id);
        if (resultado.equals("Cliente no encontrado")) {
            // 404 Not Found: Si el cliente no existe, devuelve 404
            return ResponseEntity.notFound().build();
        }
        // 200 OK: Si se elimina correctamente, devuelve 200 con mensaje
        return ResponseEntity.ok(resultado);
    }
}
