package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IProductoService {
   List<ProductoSalidaDto> listarProductos();
    ProductoSalidaDto crearProducto(ProductoEntradaDto producto) throws BadRequestException;
    void eliminarProductoPorId(Long id) ;
   ProductoSalidaDto buscarProductoPorId(Long id);

}
