package org.esteban.springboot.springmvc.app.msvcusuarios.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.CreateUsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.models.dto.UsuarioDTO;
import org.esteban.springboot.springmvc.app.msvcusuarios.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> detalle(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO) {
        UsuarioDTO nuevoUsuario = userService.create(createUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO, @PathVariable Long id) {
        return userService.update(id, createUsuarioDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (userService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.findByIds(ids
        ));
    }
}
