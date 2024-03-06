package com.dh.Xplorando.service.impl;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.entrada.UsuarioEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.ProductoSalidaDto;
import com.dh.Xplorando.dto.salida.UsuarioSalidaDto;
import com.dh.Xplorando.entity.Categoria;
import com.dh.Xplorando.entity.Usuario;
import com.dh.Xplorando.exceptions.ResourceNotFoundException;
import com.dh.Xplorando.repository.UsuarioRepository;
import com.dh.Xplorando.service.IUsuarioService;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    private final Logger LOGGER= LoggerFactory.getLogger(UsuarioService.class);
    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<UsuarioSalidaDto> listarUsuarios() {
            List<UsuarioSalidaDto> usuarios = usuarioRepository.findAll().stream().map(this::entidadADto).toList();
        LOGGER.info("LISTA DE LOS USUARIOS : {}", usuarios);
        return usuarios;
    }

    @Override
    public UsuarioSalidaDto registrarUsuario(UsuarioEntradaDto usuario) throws BadRequestException {
        Usuario usuarioGuardado = usuarioRepository.save(dtoEntradaAEntidad(usuario));
        UsuarioSalidaDto usuarioSalidaDto = entidadADto(usuarioGuardado);
        LOGGER.info("SE HA REGISTRADO UN NUEVO USUARIO: {}", usuarioSalidaDto);
        return usuarioSalidaDto;
    }

    @Override
    public UsuarioSalidaDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException {
        return null;
    }




   //MAPEO
    private Usuario dtoEntradaAEntidad(UsuarioEntradaDto usuarioEntradaDto) {
       return modelMapper.map(usuarioEntradaDto, Usuario.class);
   }


    public UsuarioSalidaDto entidadADto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioSalidaDto.class);
    }

}
