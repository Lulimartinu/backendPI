package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.*;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.CategoriaRepository;
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

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;
    private final CategoriaService categoriaService;
    private final ImagenService imagenService;


    //@Autowired se utiliza para inyectar objetos en otros objetos. Esto permite un acoplamiento suelto entre componentes y ayuda a mantener el código más mantenible.
    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ModelMapper modelMapper, CategoriaService categoriaService, ImagenService imagenService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
        this.categoriaService = categoriaService;
        this.imagenService = imagenService;
        // configureMapping();
    }

    //LISTAR-DETALALR (MEDIA)
  /*  @Override
    public List<ProductoSalidaDto> listarProductos() {
        List<ProductoSalidaDto> productos = productoRepository.findAll().stream().map(this::entidadAProductoSalidaDto).toList();
        LOGGER.info("lista de todos los paquetes disponibles :{}", productos);
        return productos;
    }*/

    @Override
    public List<ProductoSalidaDto> listarProductos() {
        return null;
    }

    //CREAR-REGISTRAR PRODUCTO (ALTA)
    @Override
    public ProductoSalidaDto crearProducto(ProductoEntradaDto producto) throws BadRequestException {

        Categoria categoria = categoriaRepository.findById(producto.getCategoriaId()).orElse(null);
        // TODO: BORRAR
        // ImagenSalidaDto imagen = imagenService.buscarImagenPorId(producto.getImagenId());

        if (categoria == null ) {
            LOGGER.error("No se encuentra la Categoría en nuestra BDD");
            throw new BadRequestException("No se encuentra la Categoría en nuestra BDD");
        }

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Producto productoCreado = productoRepository.save(modelMapper.map(producto, Producto.class));
        productoCreado.setCategoria(categoria);
        ProductoSalidaDto productoSalidaDto = entidadADto(productoCreado);
        LOGGER.info("Nuevo producto registrado con exito: {}", productoSalidaDto);

        return productoSalidaDto;
    }



        //ELIMINAR PRODUCTO (ALTA)
  /*  @Override
    public void eliminarProductoPorId(Long id)throws ResourceNotFoundException {
        if (buscarProductoPorId(id) != null){
            productoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paquete coon el id " + id);
        }
        else{
            LOGGER.error("No se ha encontrado un paquete con el id " + id + " en la BDD");
        }

    }*/

        //BUSCAR PRODUCTO (MEDIA)
   /* @Override
    public ProductoSalidaDto buscarProductoPorId(Long id){
        Producto productoBuscado = productoRepository.findById(id).orElse(null);
        ProductoSalidaDto productoSalidaDto = null;

        if (productoBuscado != null) {
            productoSalidaDto = entidadADto(productoBuscado);
            LOGGER.info("Se ha encontrado el paquete ", productoSalidaDto);
        } else {
            LOGGER.error("No se ha encontrado en la BDD un paquete con ese id " + id);
        }
        return productoSalidaDto;
    }
*/

        //MAPEO
  /* private void configureMapping() {

       modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.map(ProductoEntradaDto::getImagenEntradaDto, Producto::setImagenes));
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenSalidaDto));

        //traer de que categoría es
       modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper -> mapper.map(ProductoEntradaDto::getCategoriaEntradaDto, Producto::setCategoria));
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Producto::getCategoria, ProductoSalidaDto::setCategoriaSalidaDto));*/
    //}

    @Override
    public void eliminarProductoPorId(Long id) throws ResourceNotFoundException {

    }

   /* private Producto productoEntradaDtoaEntidad(ProductoEntradaDto productoEntradaDto) {
        return modelMapper.map(productoEntradaDto, Producto.class);
    }

    private ProductoSalidaDto entidadAProductoSalidaDto(Producto producto) {
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }
*/

  /*  @Override
    public void eliminarProductoPorId(Long id) throws ResourceNotFoundException {

    }*/

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        return null;
    }


    private CategoriaProductoSalidaDto categoriaSalidaDtoASalidaProductoDto(Long id) {
        return modelMapper.map(categoriaService.buscarCategoriaPorId(id), CategoriaProductoSalidaDto.class);
    }

    private ImagenProductoSalidaDto imagenSalidaDtoASalidaProductoDto(Long id) {
        return modelMapper.map(imagenService.buscarImagenPorId(id), ImagenProductoSalidaDto.class);
    }

    private ProductoSalidaDto entidadADto(Producto producto){
        ProductoSalidaDto productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);
        productoSalidaDto.setCategoriaProductoSalidaDto(categoriaSalidaDtoASalidaProductoDto(producto.getCategoria().getId()));
        return productoSalidaDto;
    }

}


