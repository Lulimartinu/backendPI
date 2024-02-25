package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.ProductoRepository;
import com.dh.Xplorando.service.IProductoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    private final ModelMapper modelMapper;

    @Autowired
    //@Autowired se utiliza para inyectar objetos en otros objetos. Esto permite un acoplamiento suelto entre componentes y ayuda a mantener el código más mantenible.

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
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
        Producto productoNuevo = productoRepository.save(productoEntradaDtoaEntidad(producto));
        ProductoSalidaDto productoSalidaDto = entidadAProductoSalidaDto(productoNuevo);
        LOGGER.info("Se ha creado el paquete con éxito ", productoSalidaDto);
        return productoSalidaDto;
    }

    //ELIMINAR PRODUCTO (ALTA)
    @Override
    public void eliminarProductoPorId(Long id)throws ResourceNotFoundException {
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
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {  Producto productoBuscado = productoRepository.findById(id).orElse(null);
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
    private void configureMapping() {
        //traer imagenes
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.map(ProductoEntradaDto::getImagenEntradaDto, Producto::setImagenes));
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenSalidaDto));

        //traer de que categoría es
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.map(ProductoEntradaDto::getCategoriaEntradaDto, Producto::setCategoria));
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getCategoria, ProductoSalidaDto::setCategoriaSalidaDto));
    }

    private Producto productoEntradaDtoaEntidad(ProductoEntradaDto productoEntradaDto) {
        return modelMapper.map(productoEntradaDto, Producto.class);
    }

    private ProductoSalidaDto entidadAProductoSalidaDto(Producto producto) {
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }


}
