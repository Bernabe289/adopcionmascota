package com.example.notificacionservice.Controller;

import com.example.notificacionservice.Model.Notificacion;
import com.example.notificacionservice.Service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> getNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.listarNotificaciones();

        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(notificaciones);
    }

    @PostMapping
    public ResponseEntity<?> createNotificacion(@Valid @RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.guardarNotificacion(notificacion);

        if (nuevaNotificacion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la notificación. Verifique que tenga un usuario asociado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNotificacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> getId(@PathVariable Integer id) {
        Notificacion notificacion = notificacionService.buscarPorId(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotificacion(@PathVariable Integer id, @Valid @RequestBody Notificacion notificacion) {
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
    public ResponseEntity<String> deleteNotificacion(@PathVariable Integer id) {
        boolean eliminado = notificacionService.eliminarNotificacion(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}