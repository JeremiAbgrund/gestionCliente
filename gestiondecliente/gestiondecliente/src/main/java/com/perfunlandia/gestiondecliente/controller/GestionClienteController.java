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
        // 200 OK: Siempre devuelve 200, aunque la lista esté vacía
        return ResponseEntity.ok(clientes);
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.buscarCliente(id)
                .map(ResponseEntity::ok) // 200 OK: Si el cliente existe, lo devuelve(como optional)
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found: Si no existe, devuelve 404
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
