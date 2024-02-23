package com.dh.Xplorando.controller;

import com.dh.Xplorando.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productos")
@CrossOrigin
//crossOrigin ?
public class ProductoController {
    //El controler es el responsable de manejar las solicitudes HTTP y enviar respuestas

    //es el artefacto principal de nuestros servicios REST

@Autowired
    private final IProductoService productoService;
    public ProductoController(IProductoService productoService){
        this.productoService = productoService;
    }


}
