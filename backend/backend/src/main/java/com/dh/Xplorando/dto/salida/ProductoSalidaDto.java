package com.dh.Xplorando.dto.salida;


import com.dh.Xplorando.dto.entrada.CaracteristicaEntradaDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private List<ImagenSalidaDto> imagenSalidaDtoList;

    private CategoriaProductoSalidaDto categoriaProductoSalidaDto;

    @JsonProperty("caracteristicas")
    private Set<CaracteristicaSalidaDto> caracteristicaSalidaDtos= new HashSet<>();

}

