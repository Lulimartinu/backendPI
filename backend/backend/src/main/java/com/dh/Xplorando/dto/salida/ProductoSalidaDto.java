package com.dh.Xplorando.dto.salida;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoSalidaDto {

    private Long id;

    private int codigoProducto;

    private String nombreProducto;

    private String descripcionProducto;

    private double precioProducto;

    private String direccion;

    private ImagenProductoSalidaDto imagenProductoSalidaDto;

    private CategoriaProductoSalidaDto categoriaProductoSalidaDto;
}

