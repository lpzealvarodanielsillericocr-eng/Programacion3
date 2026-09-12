package com.unifranz.programaciontres.application.service.Impl;

import com.unifranz.programaciontres.application.dto.UsuarioDto;
import com.unifranz.programaciontres.application.service.UsuarioService;
import com.unifranz.programaciontres.domain.Usuario;
import com.unifranz.programaciontres.domain.UsuarioAdmin;
import com.unifranz.programaciontres.infrastructure.persistence.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDto guardar(UsuarioDto usuarioDto) {

        Usuario usuario;

        // Si el rol es ADMIN, se crea un UsuarioAdmin
        if ("ADMIN".equalsIgnoreCase(usuarioDto.getRol())) {
            usuario = new UsuarioAdmin();
        } else {
            // Si no es ADMIN, se crea un Usuario normal
            usuario = new Usuario();
        }

        usuario.setNombre(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());

        Usuario guardar = usuarioRepository.save(usuario);

        return new UsuarioDto(guardar);
    }

    @Override
    public List<UsuarioDto> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioDto(u))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDto> listarActivos() {
        return usuarioRepository.listarActivos();
    }

    @Override 
    public UsuarioDto editar(long id, UsuarioDto usuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un usuario con ese Id" + id));

        if (Boolean.TRUE.equals(usuarioDto.getEliminado())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede editar un usuario eliminado");
        } 
        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setEmail(usuarioDto.getEmail());
        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return new UsuarioDto(usuarioActualizado);
    }
}