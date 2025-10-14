package org.esteban.springboot.springmvc.app.msvcusuarios.services;

import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.CreateUsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.UsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UsuarioDTO> findAll();
    Optional<UsuarioDTO> findById(Long id);
    UsuarioDTO create(CreateUsuarioDTO createUsuarioDTO);
    Optional<UsuarioDTO> update(Long id, CreateUsuarioDTO createUsuarioDTO);
    Iterable<UsuarioDTO> findByIds(Iterable<Long> ids);
    boolean delete(Long id);
}
