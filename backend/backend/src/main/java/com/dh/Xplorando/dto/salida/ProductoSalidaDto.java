package com.dh.Xplorando.dto.salida;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductoSalidaDto {

    private Long id;

    private int codigoProducto;

    private String nombreProducto;

    private String descripcionProducto;

    private double precioProducto;

    private String direccion;

    private ImagenSalidaDto imagenSalidaDto;

    private CategoriaSalidaDto categoriaSalidaDto;
}

