package com.unifranz.programaciontres.application.service;

import com.unifranz.programaciontres.application.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    UsuarioDto guardar(UsuarioDto usuarioDto);

    List<UsuarioDto> listar();

    List<UsuarioDto> listarActivos();

    UsuarioDto editar(Long id, UsuarioDto usuarioDto);

    void eliminarLogico(Long id);

    void eliminarFisico(Long id);
}