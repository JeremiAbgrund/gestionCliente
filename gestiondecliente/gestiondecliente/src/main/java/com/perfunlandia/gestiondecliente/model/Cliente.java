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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min = 2, max = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
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
