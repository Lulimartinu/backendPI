package com.dh.Xplorando.controller;

import com.dh.Xplorando.dto.entrada.CategoriaEntradaDto;
import com.dh.Xplorando.dto.entrada.UsuarioEntradaDto;
import com.dh.Xplorando.dto.salida.CategoriaSalidaDto;
import com.dh.Xplorando.dto.salida.UsuarioSalidaDto;
import com.dh.Xplorando.service.IUsuarioService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    private final IUsuarioService iUsuarioService;

    public UsuarioController(IUsuarioService iUsuarioService) {
        this.iUsuarioService = iUsuarioService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<UsuarioSalidaDto> registrarUsuario(@Valid @RequestBody UsuarioEntradaDto usuarioEntradaDto) throws BadRequestException {
        return new ResponseEntity<>(iUsuarioService.registrarUsuario(usuarioEntradaDto), HttpStatus.CREATED);
    }
}
