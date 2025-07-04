package com.perfunlandia.gestiondecliente.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.perfunlandia.gestiondecliente.controller.GestionClienteController;
import com.perfunlandia.gestiondecliente.model.Cliente;

@Component
public class ClienteModelAssembler 
        implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public @NonNull EntityModel<Cliente> toModel(@NonNull Cliente cliente) {
        return EntityModel.of(
            cliente,
            linkTo(methodOn(GestionClienteController.class)
                   .obtenerClientePorId(cliente.getId())).withSelfRel(),
            linkTo(methodOn(GestionClienteController.class)
                   .listarClientes()).withRel("clientes"),
            linkTo(methodOn(GestionClienteController.class)
                   .actualizarCliente(cliente.getId(), cliente)).withRel("actualizar"),
            linkTo(methodOn(GestionClienteController.class)
                   .eliminarCliente(cliente.getId())).withRel("eliminar") 
        );
    }
}
