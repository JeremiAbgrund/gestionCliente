package com.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfunlandia.gestiondecliente.model.Cliente;
import com.perfunlandia.gestiondecliente.repository.ClienteRepository;
import com.perfunlandia.gestiondecliente.services.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTests {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks //Inyecta los mocks en el servicio
    private ClienteService clienteService;

    @Test
    void listarClientes_retornaLista() {
        // Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setNombre("Juan");
        cliente1.setEmail("juan@email.com");
        
        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNombre("María");
        cliente2.setEmail("maria@email.com");
        
        List<Cliente> clientes = List.of(cliente1, cliente2); //Lista de clientes
        when(clienteRepository.findAll()).thenReturn(clientes); //Cuando se llame a findAll, se devolverá la lista de clientes

        // Act
        List<Cliente> resultado = clienteService.listarClientes();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("María", resultado.get(1).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarCliente_existente_devuelveCliente() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> resultado = clienteService.buscarCliente(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    void buscarCliente_inexistente_devuelveVacio() {
        // Arrange
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> resultado = clienteService.buscarCliente(999);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(clienteRepository, times(1)).findById(999);
    }

    @Test
    void agregarCliente_exitoso_devuelveMensajeCorrecto() {
        // Arrange
        Cliente clienteNuevo = new Cliente();
        clienteNuevo.setNombre("Nuevo Cliente");
        clienteNuevo.setEmail("nuevo@email.com");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteNuevo);

        // Act
        String resultado = clienteService.agregarCliente(clienteNuevo);

        // Assert
        assertTrue(resultado.contains("Cliente agregado exitosamente"));
        verify(clienteRepository, times(1)).save(clienteNuevo);
    }

    @Test
    void actualizarCliente_existente_devuelveMensajeCorrecto() {
        // Arrange
        Cliente clienteExistente = new Cliente();
        clienteExistente.setId(1);
        clienteExistente.setNombre("Cliente Original");
        
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Cliente Actualizado");
        
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

        // Act
        String resultado = clienteService.actualizarCliente(1, clienteActualizado);

        // Assert
        assertTrue(resultado.contains("Cliente actualizado exitosamente"));
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void actualizarCliente_inexistente_devuelveMensajeError() {
        // Arrange
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setNombre("Cliente Actualizado");
        
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        String resultado = clienteService.actualizarCliente(999, clienteActualizado);

        // Assert
        assertEquals("Cliente no encontrado", resultado);
        verify(clienteRepository, times(1)).findById(999);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void eliminarCliente_existente_devuelveMensajeCorrecto() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Cliente a Eliminar");
        
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1);

        // Act
        String resultado = clienteService.eliminarCliente(1);

        // Assert
        assertTrue(resultado.contains("Cliente eliminado correctamente"));
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarCliente_inexistente_devuelveMensajeError() {
        // Arrange
        when(clienteRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        String resultado = clienteService.eliminarCliente(999);

        // Assert
        assertEquals("Cliente no encontrado", resultado);
        verify(clienteRepository, times(1)).findById(999);
        verify(clienteRepository, never()).deleteById(anyInt());
    }
} 