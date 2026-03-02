package org.esteban.springboot.springmvc.app.msvcusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url = "${msvc.cursos.url}")
public interface CursoClientRest {
    @DeleteMapping("/api/cursos/eliminar-usuario/{usuarioId}")
    void eliminarCursoUsuarioPorId(@PathVariable Long usuarioId);
}

