package org.esteban.springboot.springmvc.app.msvcusuarios.repositories;

import org.esteban.springboot.springmvc.app.msvcusuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario,Long> {
}
