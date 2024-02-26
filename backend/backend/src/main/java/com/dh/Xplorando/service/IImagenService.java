package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ImagenEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.ImagenSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IImagenService {


    ImagenSalidaDto crearImagen(ImagenEntradaDto imagenEntradaDto) ;


    ImagenSalidaDto buscarImagenPorId(Long id);
}
