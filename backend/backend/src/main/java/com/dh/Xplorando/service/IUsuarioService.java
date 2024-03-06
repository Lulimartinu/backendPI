package com.dh.Xplorando.service;

import com.dh.Xplorando.dto.entrada.ProductoEntradaDto;
import com.dh.Xplorando.dto.entrada.UsuarioEntradaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.dto.salida.UsuarioSalidaDto;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioSalidaDto> listarUsuarios();
    UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) throws BadRequestException;
    UsuarioSalidaDto buscarUsuarioPorId(Long id)throws ResourceNotFoundException;

}
