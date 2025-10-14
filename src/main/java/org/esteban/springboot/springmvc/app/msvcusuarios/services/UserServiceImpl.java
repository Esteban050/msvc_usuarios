package org.esteban.springboot.springmvc.app.msvcusuarios.services;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository repository;

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
            repository.deleteById(id);
            return true;
        }
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
