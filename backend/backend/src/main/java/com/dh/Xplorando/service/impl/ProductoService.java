package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.repository.ProductoRepository;
import com.dh.Xplorando.service.IProductoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    //LISTAR-DETALALR (MEDIA)
    @Override
    public List<ProductoSalidaDto> listarProductos() {
        List<ProductoSalidaDto> productos = productoRepository.findAll().stream().map(this::entidadAProductoSalidaDto).toList();
        LOGGER.info("lista de todos los paquetes disponibles :{}", productos);
        return productos;
    }

    //CREAR-REGISTRAR PRODUCTO (ALTA)
    @Override
    public ProductoSalidaDto crearProducto(ProductoEntradaDto producto) throws BadRequestException {
        Producto producto1 = productoRepository.save(productoEntradaDtoaEntidad(producto));
        ProductoSalidaDto productoSalidaDto = entidadAProductoSalidaDto(producto1);
        LOGGER.info("Se ha creado el paquete con éxito ", productoSalidaDto);
        return productoSalidaDto;
    }

    //ELIMINAR PRODUCTO (ALTA)
    @Override
    public void eliminarProductoPorId(Long id) {
        if (buscarProductoPorId(id) != null){
            productoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paquete coon el id " + id);
        }
        else{
            LOGGER.error("No se ha encontrado un paquete con el id " + id + " en la BDD");
        }

    }

    //BUSCAR PRODUCTO (MEDIA)
    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) {  Producto productoBuscado = productoRepository.findById(id).orElse(null);
        ProductoSalidaDto productoSalidaDto = null;
        if (productoBuscado != null) {
            productoSalidaDto = entidadAProductoSalidaDto(productoBuscado);
            LOGGER.info("Se ha encontrado el paquete ", productoSalidaDto);
        } else {
            LOGGER.error("No se ha encontrado en la BDD un paquete con ese id " + id);
        }
        return productoSalidaDto;
    }


    //MAPEO
    private Producto productoEntradaDtoaEntidad(ProductoEntradaDto pacienteEntradaDto) {
        return modelMapper.map(productoRepository, Producto.class);
    }

    private ProductoSalidaDto entidadAProductoSalidaDto(Producto producto) {
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }


}
