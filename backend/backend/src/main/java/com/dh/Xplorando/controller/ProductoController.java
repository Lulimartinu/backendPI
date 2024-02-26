package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
@CrossOrigin
//crossOrigin ?
public class ProductoController {
    //El controler es el responsable de manejar las solicitudes HTTP y enviar respuestas

    //es el artefacto principal de nuestros servicios REST

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<ProductoSalidaDto> crearTurno(@Valid @RequestBody ProductoEntradaDto producto) throws BadRequestException {
        return new ResponseEntity<>(productoService.crearProducto(producto), HttpStatus.CREATED);
    }

    ;
}





