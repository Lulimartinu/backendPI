package com.dh.Xplorando.dto.entrada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UsuarioEntradaDto {
    @NotNull
    @Size(min = 1, max = 250)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 250)
    private String apellido;

    //agregar validaciones para mail
    @NotNull
    @Size(min = 1, max = 250)
    private String mail;

    @NotNull
    @Size(min = 1, max = 250)
    private String contraseña;
    private boolean esAdmin= false;

}
