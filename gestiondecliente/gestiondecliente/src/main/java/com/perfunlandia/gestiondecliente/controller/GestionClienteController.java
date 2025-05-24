package com.perfunlandia.gestiondecliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class GestionClienteController {

    @Autowired
    private ClienteService clienteService;

    // Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes); // Devuelve una lista vacía si no hay clientes
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.buscarCliente(id)
                .map(ResponseEntity::ok) // Si el cliente está presente, devuelve 200 OK con el cliente
                .orElse(ResponseEntity.notFound().build()); // Si no está presente, devuelve 404 Not Found
    }

    // Agregar nuevo cliente
    @PostMapping
    public ResponseEntity<String> agregarCliente(@RequestBody Cliente clienteNuevo) {
        try {
            String resultado = clienteService.agregarCliente(clienteNuevo);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al agregar el cliente: " + e.getMessage());
        }
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        String resultado = clienteService.actualizarCliente(id, clienteActualizado);
        if (resultado.equals("Cliente no encontrado")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable int id) {
        String resultado = clienteService.eliminarCliente(id);
        if (resultado.equals("Cliente no encontrado")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }
}
