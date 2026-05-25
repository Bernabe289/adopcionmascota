package com.example.historialvetservice.Controller;

import com.example.historialvetservice.Model.HistorialVet;
import com.example.historialvetservice.Service.HistorialVetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.NotYetBoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/historiales")
public class HistorialVetController {

    @Autowired
    private HistorialVetService historialVetService;

    @GetMapping
    public ResponseEntity<List<HistorialVet>> getHistoriales() {
        List<HistorialVet> historiales = historialVetService.listarHistoriales();

        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historiales);
    }

    @PostMapping
    public ResponseEntity<?> createHistorial(@Valid @RequestBody HistorialVet historialVet) {
        HistorialVet nuevoHistorial = historialVetService.guardarHistorial(historialVet);

        if (nuevoHistorial == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar el historial. Verifique que tenga una mascota asignada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHistorial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialVet> getId(@PathVariable Integer id) {
        HistorialVet historialVet = historialVetService.buscarPorId(id);

        if (historialVet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historialVet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistorial(@PathVariable Integer id, @Valid @RequestBody HistorialVet historialVet) {
        HistorialVet historialExistente = historialVetService.buscarPorId(id);

        if (historialExistente == null) {
            return ResponseEntity.notFound().build();
        }

        HistorialVet historialActualizado = historialVetService.actualizarHistorial(id, historialVet);

        if (historialActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el historial. Verifique que tenga una mascota asignada.");
        }

        return ResponseEntity.ok(historialActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHistorial(@PathVariable Integer id) {
        boolean eliminado = historialVetService.eliminarHistorial(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}