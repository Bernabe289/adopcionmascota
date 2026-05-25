package com.example.solicitudservice.Controller;

import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Service.SolicitudAdopcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes")
public class SolicitudAdopcionController {

    @Autowired
    private SolicitudAdopcionService solicitudAdopcionService;

    @GetMapping
    public ResponseEntity<List<SolicitudAdopcion>> getSolicitudes() {
        List<SolicitudAdopcion> solicitudes = solicitudAdopcionService.listarSolicitudes();

        if (solicitudes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping
    public ResponseEntity<?> createSolicitud(@Valid @RequestBody SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion nuevaSolicitud = solicitudAdopcionService.guardarSolicitud(solicitudAdopcion);

        if (nuevaSolicitud == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la solicitud. Verifique usuario, mascota y datos obligatorios.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudAdopcion> getSolicitudById(@PathVariable Integer id) {
        SolicitudAdopcion solicitudAdopcion = solicitudAdopcionService.buscarPorId(id);

        if (solicitudAdopcion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(solicitudAdopcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSolicitud(@PathVariable Integer id, @Valid @RequestBody SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion solicitudActualizada = solicitudAdopcionService.actualizarSolicitud(id, solicitudAdopcion);

        if (solicitudActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la solicitud. Verifique que exista y/o que los datos sean correctos.");
        }

        return ResponseEntity.ok(solicitudActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSolicitud(@PathVariable Integer id) {
        boolean eliminado = solicitudAdopcionService.eliminarSolicitud(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada.");
        }

        return ResponseEntity.ok("Solicitud eliminada correctamente.");
    }
}
