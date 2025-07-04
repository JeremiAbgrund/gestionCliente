package com.perfunlandia.gestiondecliente.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.perfunlandia.gestiondecliente.controller.GestionClienteController;
import com.perfunlandia.gestiondecliente.model.Cliente;

@Component // Anotación para indicar que esta clase es un componente de Spring
public class ClienteModelAssembler  // Clase que implementa RepresentationModelAssembler para convertir objetos Cliente a EntityModel<Cliente>
        implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> { // Especifica que convierte Cliente a EntityModel<Cliente>

    @Override
    public @NonNull EntityModel<Cliente> toModel(@NonNull Cliente cliente) { // Método que convierte un objeto Cliente a EntityModel<Cliente>
        return EntityModel.of( // Crea un EntityModel a partir del cliente
            cliente, // Incluye el cliente en el EntityModel
            linkTo(methodOn(GestionClienteController.class) // Crea un enlace a la acción de obtener cliente por ID
                   .obtenerClientePorId(cliente.getId())).withSelfRel(), // Enlace a sí mismo
            linkTo(methodOn(GestionClienteController.class) // Crea un enlace a la acción de listar todos los clientes
                   .listarClientes()).withRel("clientes"), // Enlace a la lista de clientes
            linkTo(methodOn(GestionClienteController.class) // Crea un enlace a la acción de agregar un nuevo cliente
                   .actualizarCliente(cliente.getId(), cliente)).withRel("actualizar"), // Enlace para actualizar el cliente
            linkTo(methodOn(GestionClienteController.class) // Crea un enlace a la acción de eliminar un cliente
                   .eliminarCliente(cliente.getId())).withRel("eliminar")  // Enlace para eliminar el cliente
        );
    }
}
