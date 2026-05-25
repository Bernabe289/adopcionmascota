package com.example.visitaservice.Controller;

import com.example.visitaservice.Model.Visita;
import com.example.visitaservice.Service.VisitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @GetMapping
    public ResponseEntity<List<Visita>> getVisitas() {
        List<Visita> visitas = visitaService.listarVisitas();

        if (visitas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(visitas);
    }

    @PostMapping
    public ResponseEntity<?> createVisita(@Valid @RequestBody Visita visita) {
        Visita nuevaVisita = visitaService.guardarVisita(visita);

        if (nuevaVisita == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la visita. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVisita);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> getId(@PathVariable Integer id) {
        Visita visita = visitaService.buscarPorId(id);

        if (visita == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(visita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVisita(@PathVariable Integer id, @Valid @RequestBody Visita visita) {
        Visita visitaExistente = visitaService.buscarPorId(id);

        if (visitaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Visita visitaActualizada = visitaService.actualizarVisita(id, visita);

        if (visitaActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la visita. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.ok(visitaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVisita(@PathVariable Integer id) {
        boolean eliminado = visitaService.eliminarVisita(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
