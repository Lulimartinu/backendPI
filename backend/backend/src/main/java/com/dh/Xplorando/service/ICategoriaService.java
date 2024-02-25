package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface ICategoriaService {

    CategoriaSalidaDto crearCategoria(CategoriaEntradaDto categoriaEntradaDto) throws BadRequestException;
}
