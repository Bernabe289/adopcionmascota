package com.example.notificacionservice.Controller;

import com.example.notificacionservice.Model.Notificacion;
import com.example.notificacionservice.Service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificacion", description = "API para la gestion de notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    @Operation(
            summary = "Listar notificaciones",
            description = "Obtiene la lista de todas las notificaciones registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones encontradas con exito"),
            @ApiResponse(responseCode = "204", description = "No hay notificaciones dentro del sistema")
    })
    public ResponseEntity<List<Notificacion>> getNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.listarNotificaciones();

        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notificaciones);
    }

    @PostMapping
    @Operation(
            summary = "Crear notificacion",
            description = "Registra notificaciones dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Esta notificacion ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "Notificacion creada con exito")
    })
    public ResponseEntity<?> createNotificacion(@Valid @RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.guardarNotificacion(notificacion);

        if (nuevaNotificacion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la notificación. Verifique que tenga un usuario asociado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNotificacion);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar notificacion",
            description = "Buscar una notificacion por su id asociada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "200", description = "Notificacion encontrada dentro del sistema")
    })
    public ResponseEntity<Notificacion> getId(@Parameter(description = "ID de la notificacion", example = "1")
                                              @PathVariable Integer id) {
        Notificacion notificacion = notificacionService.buscarPorId(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar notificacion",
            description = "Actualiza la notificacion por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "200", description = "Notificacion actualizada con exito")
    })
    public ResponseEntity<?> updateNotificacion(@Parameter(description = "ID de la notificacion a actualizar", example = "1")
                                                @PathVariable Integer id, @Valid @RequestBody Notificacion notificacion) {
        Notificacion notificacionExistente = notificacionService.buscarPorId(id);

        if (notificacionExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Notificacion notificacionActualizada = notificacionService.actualizarNotificacion(id, notificacion);

        if (notificacionActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la notificación. Verifique que tenga un usuario asociado.");
        }

        return ResponseEntity.ok(notificacionActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar notificacion",
            description = "Elimina la notificacion asociada por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Notificacion no encontrada"),
            @ApiResponse(responseCode = "200", description = "Notificacion eliminada con exito")
    })
    public ResponseEntity<String> deleteNotificacion(@Parameter(description = "ID de la notificacion a eliminar", example = "1")
                                                     @PathVariable Integer id) {
        boolean eliminado = notificacionService.eliminarNotificacion(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}