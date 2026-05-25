package com.example.seguimientoservice.Controller;

import com.example.seguimientoservice.Model.Seguimiento;
import com.example.seguimientoservice.Service.SeguimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seguimientos")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping
    public ResponseEntity<List<Seguimiento>> getSeguimientos() {
        List<Seguimiento> seguimientos = seguimientoService.listarSeguimientos();

        if (seguimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(seguimientos);
    }

    @PostMapping
    public ResponseEntity<?> createSeguimiento(@Valid @RequestBody Seguimiento seguimiento) {
        Seguimiento nuevoSeguimiento = seguimientoService.guardarSeguimiento(seguimiento);

        if (nuevoSeguimiento == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar el seguimiento. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSeguimiento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> getId(@PathVariable Integer id) {
        Seguimiento seguimiento = seguimientoService.buscarPorId(id);

        if (seguimiento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeguimiento(@PathVariable Integer id, @Valid @RequestBody Seguimiento seguimiento) {
        Seguimiento seguimientoExistente = seguimientoService.buscarPorId(id);

        if (seguimientoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Seguimiento seguimientoActualizado = seguimientoService.actualizarSeguimiento(id, seguimiento);

        if (seguimientoActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el seguimiento. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.ok(seguimientoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeguimiento(@PathVariable Integer id) {
        boolean eliminado = seguimientoService.eliminarSeguimiento(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
