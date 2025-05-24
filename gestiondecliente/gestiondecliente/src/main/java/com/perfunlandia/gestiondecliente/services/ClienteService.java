package com.perfunlandia.gestiondecliente.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll(); // Usa el método findAll de JpaRepository
    }

    // Buscar cliente por ID
    public Optional<Cliente> buscarCliente(int id) {
        return clienteRepository.findById(id); // Usa el método findById de JpaRepository
    }

    // Agregar nuevo cliente
    @Transactional
    public String agregarCliente(Cliente clienteNuevo) {
        try {
            clienteRepository.save(clienteNuevo);
            return "Cliente agregado exitosamente";
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause != null && rootCause.getMessage() != null && rootCause.getMessage().contains("Duplicate")) {
                return "El correo ya está registrado";
            }
            return "Error al agregar cliente: " + e.getMessage();
        }
    }

    // Actualizar cliente existente
    @Transactional
    public String actualizarCliente(int id, Cliente clienteActualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido_paterno(clienteActualizado.getApellido_paterno());
            cliente.setApellido_materno(clienteActualizado.getApellido_materno());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setEmail(clienteActualizado.getEmail());
            clienteRepository.save(cliente); // Guarda los cambios en la base de datos
            return "Cliente actualizado exitosamente";
        }).orElse("Cliente no encontrado");
    }

    // Eliminar cliente por ID
    @Transactional
    public String eliminarCliente(int id) {
        return clienteRepository.findById(id).map(cliente -> {
            clienteRepository.deleteById(id);
            return "Cliente eliminado correctamente";
        }).orElse("Cliente no encontrado");
    }
}