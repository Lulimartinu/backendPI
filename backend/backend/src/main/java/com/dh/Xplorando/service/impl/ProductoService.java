package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.modificacion.ProductoModificacionEntrada;
import com.dh.Xplorando.dto.salida.*;
import com.dh.Xplorando.entity.Caracteristica;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.entity.Imagen;
import com.dh.Xplorando.entity.Producto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.CaracteristicaRepository;
import com.dh.Xplorando.repository.CategoriaRepository;
import com.dh.Xplorando.repository.ImagenRepository;
import com.dh.Xplorando.repository.ProductoRepository;
import com.dh.Xplorando.service.IProductoService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;
    //CAMBIO
    private final ImagenRepository imagenRepository;

    private final CaracteristicaRepository caracteristicaRepository;

    private final ModelMapper modelMapper;
    private final CategoriaService categoriaService;

    private final ImagenService imagenService;



    //@Autowired se utiliza para inyectar objetos en otros objetos. Esto permite un acoplamiento suelto entre componentes y ayuda a mantener el código más mantenible.
    @Autowired
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ImagenRepository imagenRepository, CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper, CategoriaService categoriaService, ImagenService imagenService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.imagenRepository = imagenRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
        this.categoriaService = categoriaService;
        this.imagenService = imagenService;
        configureMapping();
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
        List<ProductoSalidaDto> productos = productoRepository.findAll().stream().map(this::entidadADto).toList();
        LOGGER.info("LISTA DE LOS PRODUCTOS DISPONIBLES : {}" + productos);
        return productos;
    }



    //CREAR-REGISTRAR PRODUCTO (ALTA)
   /* @Override
    public ProductoSalidaDto crearProducto(ProductoEntradaDto productoEntradaDto) throws BadRequestException, DataIntegrityViolationException {
        Categoria categoria = categoriaRepository.findById(productoEntradaDto.getCategoriaId()).orElseThrow(() -> {
                    LOGGER.error("categoría no existe");
                    return new BadRequestException("categoría no existe");
                });

       /* if (categoria == null) {
            LOGGER.error("No se encuentra la Categoría en nuestra BDD");
            throw new BadRequestException("No se encuentra la Categoría en nuestra BDD");
        }
*//*

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Producto productoEntidad = modelMapper.map(productoEntradaDto, Producto.class);
        productoEntidad.setCategoria(categoria);
       // productoEntidad.setId(Long.parseLong("0"));
        List<Imagen> imagenesList = new ArrayList<>();
        for (ImagenEntradaDto imagenEntradaDto : productoEntradaDto.getImagenes()) {
            Imagen imagenEntidad = modelMapper.map(imagenEntradaDto, Imagen.class);
            LOGGER.info("imagen :" + imagenEntidad);
            imagenEntidad.setProducto(productoEntidad);
            imagenesList.add(imagenEntidad);
        }
        productoEntidad.setImagenes(imagenesList);
        Producto productoCreado = productoRepository.save(productoEntidad);
      //  productoCreado.setCategoria(categoria);

        Producto producto = productoRepository.findById(productoCreado.getId()).orElse(null);


        ProductoSalidaDto productoSalidaDto = entidadADto(producto);
        LOGGER.info("Nuevo producto registrado con exito: {}", productoSalidaDto);

        return productoSalidaDto;
    }
    */
    @Override
    public ProductoSalidaDto crearProducto(ProductoEntradaDto productoEntradaDto) throws BadRequestException, DataIntegrityViolationException, ResourceNotFoundException {

       /* Categoria categoria = categoriaRepository.findById(productoEntradaDto.getCategoriaId())
                .orElseThrow(() -> {
                    LOGGER.error("categoría no existe");
                    return new BadRequestException("Ncateroría no exist");
                });
                */
        String categoriaId = productoEntradaDto.getCategoriaString();
        Categoria categoria = categoriaRepository.findByNombreCategoria(categoriaId);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaId);
        }

        Set<Caracteristica> caracteristicasList = new HashSet<>();
        Set<String> arrayDeCaracteristicas = productoEntradaDto.getCaracteristica_nombre();
        for (String caracteristica : arrayDeCaracteristicas){
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombreCaracteristica(caracteristica);
            if (caracteristicaBuscada == null){
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            }
            caracteristicasList.add(caracteristicaBuscada);
        }

        Producto productoEntidad = modelMapper.map(productoEntradaDto, Producto.class);
        productoEntidad.setCategoria(categoria);
        productoEntidad.setCaracteristicas(caracteristicasList);


        List<Imagen> imagenesList = new ArrayList<>();
        for (ImagenEntradaDto imagenEntradaDto : productoEntradaDto.getImagenes()) {
            Imagen imagenEntidad = modelMapper.map(imagenEntradaDto, Imagen.class);
            LOGGER.info("Imagen: " + imagenEntidad);
            imagenEntidad.setProducto(productoEntidad);
            imagenesList.add(imagenEntidad);
        }
        productoEntidad.setImagenes(imagenesList);


        Producto productoCreado = productoRepository.save(productoEntidad);


        ProductoSalidaDto productoSalidaDto = entidadADto(productoCreado);
        LOGGER.info("Nuevo producto registrado con éxito: {}", productoSalidaDto);

        return productoSalidaDto;
    }

   /* @Override
    public ProductoSalidaDto editarProducto(ProductoModificacionEntrada productoModificacionEntrada) throws ResourceNotFoundException {
        Producto productoAmodificar = dtoModificacionAentidad(productoModificacionEntrada);
        Producto productoPorID = productoRepository.findById(productoAmodificar.getId()).orElse(null);

        ProductoSalidaDto productoSalidaDtoModificado = null;
        if (productoPorID !=null){
            Producto productoModificado = productoRepository.save(productoAmodificar);
            productoSalidaDtoModificado = entidadAdtoSalida((productoModificado));
            LOGGER.info("Paquete Modificado : " + productoModificado);
        }else {
            LOGGER.error("El producto no se encontró");
            throw new ResourceNotFoundException("No fue posible actualizar los datos del producto ");
        }

        return productoSalidaDtoModificado;
    }*/


    /*@Override
    public ProductoSalidaDto editarProducto(ProductoModificacionEntrada productoModificacionEntrada) throws ResourceNotFoundException {
        Long buscarProductoId = productoModificacionEntrada.getId();
        Optional<Producto> productoBuscado = productoRepository.findById(buscarProductoId);
        if (!productoBuscado.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el producto con el ID proporcionado: " + buscarProductoId);
        }
        LOGGER.info("PRODUCTO: " + productoBuscado);

        String categoriaNombre = productoModificacionEntrada.getCategoriaString();
        Categoria categoria = categoriaRepository.findByNombreCategoria(categoriaNombre);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaNombre);
        }


        // Long productoId = productoModificacionEntrada.getId();
       // Optional<Producto> productoBuscado = productoRepository.findById(productoId);
        Producto productoModificado = dtoModificacionAentidad(productoModificacionEntrada);

        // Producto productoAModificar= productoRepository.findById(productoModificacionEntrada.getId()).orElse(null);
        ProductoSalidaDto productoSalidaDto = null;

        // productoAModificar.setCategoria(modelMapper.map(categoriaService.buscarCategoriaPorId(productoModificacionEntrada.getCategoriaId()), Categoria.class));
        productoModificado.setCategoria(categoria);
        productoRepository.save(productoModificado);
        productoSalidaDto = entidadADto(productoModificado);




        LOGGER.warn("SE HA MODIFICADO EL PAQUETE {}", productoSalidaDto);


        return productoSalidaDto;
    }
*/


    @Override
    public ProductoSalidaDto editarProducto(ProductoModificacionEntrada productoModificacionEntradaDto) throws ResourceNotFoundException {

        LOGGER.info("PRODUCTO A MODIFICAR: " + productoModificacionEntradaDto);  //entra sin imags
        Long buscarProductoId = productoModificacionEntradaDto.getId();
        Optional<Producto> productoBuscado = productoRepository.findById(buscarProductoId);
        if (!productoBuscado.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el producto con el ID proporcionado: " + buscarProductoId);
        }
        LOGGER.info("PRODUCTO: " + productoBuscado);

        String categoriaTitulo = productoModificacionEntradaDto.getCategoriaString();
        Categoria categoria = categoriaRepository.findByNombreCategoria(categoriaTitulo);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaTitulo);
        }

        ProductoSalidaDto productoSalidaDto = null;

        if(productoBuscado.isPresent()) { // Verifica si el Optional contiene un valor
            LOGGER.info("PRODUCTO DENTRO DEL IF: " + productoBuscado);
            Producto productoMap = dtoModificacionAentidad(productoModificacionEntradaDto);
            productoMap.setCategoria(categoria);

            Producto guardarProducto = productoRepository.save(productoMap);
            LOGGER.info("PRODUCTO GUARDADO: " + guardarProducto);

            productoSalidaDto = entidadADto(guardarProducto);
            LOGGER.info("PRODUCTO SALIDA: " + productoSalidaDto);

            LOGGER.info("El producto " + productoMap + " fue modificado exitosamente ");
        } else {
            LOGGER.info("El producto " + buscarProductoId + " no fue encontrado.");
            throw new ResourceNotFoundException("El producto con ID: " + buscarProductoId + " no fue encontrado.");
        }
        return productoSalidaDto;
    }

    @Override
    public void eliminarProductoPorId(Long id) throws ResourceNotFoundException {

    }
   /* @Override
    public ProductoSalidaDto modificarProducto(ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException {



        Set<Caracteristica> caracteristicasList = new HashSet<>();
        Set<String> arrayDeCaracteristicas = productoModificacionEntradaDto.getCaracteristicas();
        for (String caracteristica : arrayDeCaracteristicas) {
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombre(caracteristica);
            if (caracteristicaBuscada == null) {
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            } caracteristicasList.add(caracteristicaBuscada);
        }
        ProductoSalidaDto productoSalidaDto = null;

        if(productoBuscado != null){
            LOGGER.info("PRODUCTO DENTRO DEL IF: " + productoBuscado);
            Producto productoMap = dtoModificacioAentidad(productoModificacionEntradaDto);
            productoMap.setCategoria(categoria);
            productoMap.setCaracteristicas(caracteristicasList);
            LOGGER.info("PRODUCTO MAPEADO: " + productoMap);
            productoMap.setImagenes(productoBuscado.get().getImagenes());
            LOGGER.info("PRODUCTO SETEADO: " + productoMap);

            Producto guardarProducto = productoRepository.save(productoMap);
            LOGGER.info("PRODUCTO GUARDADO: " + guardarProducto);

            productoSalidaDto = entidadAdtoSalida(guardarProducto);
            LOGGER.info("PRODUCTO SALIDA: " + productoSalidaDto);

            LOGGER.info("El producto " + productoMap + " fue modificado exitosamente ");
        } else {
            LOGGER.info("El producto " + buscarProductoId + " no fue encontrado.");
            throw new ResourceNotFoundException("El producto: " + productoBuscado + "  no fue encontrado.");
        }
        return productoSalidaDto;
    }*/



    //BUSCAR PRODUCTO (MEDIA)
   @Override
    public ProductoSalidaDto buscarProductoPorId(Long id){
        Producto productoBuscado = productoRepository.findById(id).orElse(null);
        ProductoSalidaDto productoEncontrado = null;
        if (productoBuscado != null) {
           //cambie entidad a dto
            productoEncontrado = entidadADto(productoBuscado);
            LOGGER.info("Se ha encontrado el paquete: " + productoEncontrado);
        } else {
            LOGGER.error("No se ha encontrado en la BDD un paquete con ese id " + id);
        }
        return productoEncontrado;
    }




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

    /*@Override
    public void eliminarProductoPorId(Long id) throws ResourceNotFoundException {

        if (buscarProductoPorId(id) != null){
            LOGGER.warn("Se eliminó el paquete con el id : " + dtoSalidaAentidad(buscarProductoPorId(id)));
            productoRepository.deleteById(id);
        }else {
            LOGGER.error("No se encontró el paquete con el id : " + id);
            throw new ResourceNotFoundException("No se encontró el paquete con el id : " + id);
        }

    }*/

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




    private void configureMapping(){
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Producto::getCategoria,ProductoSalidaDto::setCategoriaSalidaDto);
                    mapper.map(Producto::getCaracteristicas,ProductoSalidaDto::setCaracteristicaSalidaDtos);
                });

    }


   private CategoriaProductoSalidaDto categoriaSalidaDtoASalidaProductoDto(Long id) {
        return modelMapper.map(categoriaService.buscarCategoriaPorId(id), CategoriaProductoSalidaDto.class);
    }

    private List<ImagenSalidaDto> imagenSalidaDtoASalidaProductoDto(List<Imagen> imagenList) {
        List<ImagenSalidaDto> imagenListDto = new ArrayList<ImagenSalidaDto>();

        for (Imagen imagen : imagenList) {
            ImagenSalidaDto imagenSalidaDto = modelMapper.map(imagen, ImagenSalidaDto.class);
            imagenListDto.add(imagenSalidaDto);
        }

        return imagenListDto;
    }

    public Producto dtoModificacionAentidad (ProductoModificacionEntrada productoModificacionEntrada){
        return modelMapper.map(productoModificacionEntrada,Producto.class);
    }

    /*private Producto dtoSalidaAentidad (ProductoSalidaDto productoSalidaDto){
        return modelMapper.map(productoSalidaDto, Producto.class);
    }*/
    private ProductoSalidaDto entidadADto(Producto producto) {
        ProductoSalidaDto productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);
      // productoSalidaDto.setCategoriaSalidaDto(categoriaSalidaDtoASalidaProductoDto(producto.getCategoria().getId()));

        var imagenSalidaDtoList = imagenSalidaDtoASalidaProductoDto(producto.getImagenes());
        productoSalidaDto.setImagenSalidaDtoList(imagenSalidaDtoList);

        return productoSalidaDto;
    }

}


