package com.perfunlandia.gestiondecliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Genera un constructor con todos los parámetros
@NoArgsConstructor  // Genera un constructor sin parámetros  // Genera los métodos equals y hashCode
@Entity // esta clase es una entidad JPA
@Table(name = "cliente") // Nombre de la tabla en la base de datos
public class Cliente {

    @Id // clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(nullable = false, length = 50) // nunllable es para que no pueda ser nulo a nivel de base de datos
    @NotNull // not null es para que no se pueda guardar un cliente sin nombre (en java)
    @Size(min = 2, max = 50)
    private String nombre;

    @Column(nullable = false, length = 50) // la column no puede ser nula teniendo como tamanio maximo 50 caracteres
    @NotNull 
    @Size(min = 2, max = 50)
    private String apellido_paterno;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    private String apellido_materno;

    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    private String telefono;

    @Email
    @Column(nullable = false, unique = true, length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    private String email;
}
