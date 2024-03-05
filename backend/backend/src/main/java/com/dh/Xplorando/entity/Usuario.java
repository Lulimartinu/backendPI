package com.dh.Xplorando.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="USUARIOS")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="NOMBRE")
    @NotNull
    @Size(min = 1, max = 250)
    private String nombre;
    @Column(name="APELLIDO")
    @NotNull
    @Size(min = 1, max = 250)
    private String apellido;
    @Column(name="EMAIL",unique = true)
    @NotNull
    @Size(min = 1, max = 250)
    private String mail;
    @Column(name="CONTRASEÑA")
    @NotNull
    @Size(min = 1, max = 250)
    private String contraseña;
    private boolean esAdmin;



}
