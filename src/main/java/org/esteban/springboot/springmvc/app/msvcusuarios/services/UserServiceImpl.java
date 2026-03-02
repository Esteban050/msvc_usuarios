package org.esteban.springboot.springmvc.app.msvcusuarios.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.esteban.springboot.springmvc.app.msvcusuarios.client.CursoClientRest;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.CreateUsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.UsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.entity.Usuario;
import org.esteban.springboot.springmvc.app.msvcusuarios.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository repository;
    private final CursoClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    @Override
    @Transactional
    public UsuarioDTO create(CreateUsuarioDTO createUsuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(createUsuarioDTO.getNombre());
        usuario.setEmail(createUsuarioDTO.getEmail());
        usuario.setPassword(createUsuarioDTO.getPassword());

        Usuario savedUsuario = repository.save(usuario);
        return toDTO(savedUsuario);
    }

    @Override
    @Transactional
    public Optional<UsuarioDTO> update(Long id, CreateUsuarioDTO createUsuarioDTO) {
        return repository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(createUsuarioDTO.getNombre());
                    usuario.setEmail(createUsuarioDTO.getEmail());
                    usuario.setPassword(createUsuarioDTO.getPassword());
                    return toDTO(repository.save(usuario));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<UsuarioDTO> findByIds(Iterable<Long> ids) {
        return StreamSupport.stream(repository.findAllById(ids).spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean delete(Long id) {

        if (repository.existsById(id)) {
            try {
                clientRest.eliminarCursoUsuarioPorId(id);
                log.info("Usuario con id {} eliminado exitosamente del microservicio de cursos", id);
            } catch (FeignException.NotFound e) {
                log.warn("El usuario con id {} no tiene cursos asociados o ya fueron eliminados", id);
            } catch (FeignException.ServiceUnavailable | FeignException.InternalServerError e) {
                log.error("Error de comunicación con el microservicio de cursos al eliminar usuario {}: {}",
                         id, e.getMessage());
                throw new RuntimeException("El servicio de cursos no está disponible. No se puede eliminar el usuario.", e);
            } catch (FeignException e) {
                log.error("Error inesperado al comunicarse con el microservicio de cursos para usuario {}: {}",
                         id, e.getMessage());
                throw new RuntimeException("Error al comunicarse con el servicio de cursos.", e);
            }

            repository.deleteById(id);
            log.info("Usuario con id {} eliminado exitosamente de la base de datos", id);
            return true;
        }

        log.warn("Intento de eliminar usuario con id {} que no existe", id);
        return false;
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
