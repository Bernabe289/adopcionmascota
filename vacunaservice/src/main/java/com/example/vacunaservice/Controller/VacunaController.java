package com.example.vacunaservice.Controller;

import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Service.VacunaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vacunas")
public class VacunaController {

    @Autowired
    private VacunaService vacunaService;

    @GetMapping
    public ResponseEntity<List<Vacuna>> getVacunas() {
        List<Vacuna> vacunas = vacunaService.listarVacunas();

        if (vacunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vacunas);
    }

    @PostMapping
    public ResponseEntity<?> createVacuna(@Valid @RequestBody Vacuna vacuna) {
        Vacuna nuevaVacuna = vacunaService.guardarVacuna(vacuna);

        if (nuevaVacuna == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la vacuna. Verifique que tenga un historial asociado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVacuna);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> getId(@PathVariable Integer id) {
        Vacuna vacuna = vacunaService.buscarPorId(id);

        if (vacuna == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vacuna);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVacuna(@PathVariable Integer id, @Valid @RequestBody Vacuna vacuna) {
        Vacuna vacunaExistente = vacunaService.buscarPorId(id);

        if (vacunaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Vacuna vacunaActualizada = vacunaService.actualizarVacuna(id, vacuna);

        if (vacunaActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la vacuna. Verifique que tenga un historial asociado.");
        }

        return ResponseEntity.ok(vacunaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVacuna(@PathVariable Integer id) {
        boolean eliminado = vacunaService.eliminarVacuna(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
