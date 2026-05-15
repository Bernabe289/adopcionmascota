package com.adopcion.adopcionmascota.Controller;

import com.adopcion.adopcionmascota.Model.Raza;
import com.adopcion.adopcionmascota.Service.RazaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/razas")
public class RazaController {

    @Autowired
    private RazaService razaService;

    @GetMapping
    public ResponseEntity<List<Raza>> getRazas() {
        List<Raza> razas = razaService.listarRazas();

        if (razas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(razas);
    }

    @PostMapping // Valida que la raza no esté repetida y que la especie exista
    public ResponseEntity<?> createRaza(@Valid @RequestBody Raza raza) {
        Raza nuevaRaza = razaService.guardarRaza(raza);

        if (nuevaRaza == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar la raza. Verifique que no esté repetida y que la especie exista.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRaza);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Raza> getId(@PathVariable Integer id) {
        Raza raza = razaService.buscarPorId(id);

        if (raza == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(raza);
    }

    @PutMapping("/{id}") // Actualiza solo si la raza existe, no está duplicada y tiene una especie válida
    public ResponseEntity<?> updateRaza(@PathVariable Integer id, @Valid @RequestBody Raza raza) {
        Raza razaExistente = razaService.buscarPorId(id);

        if (razaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Raza razaActualizada = razaService.actualizarRaza(id, raza);

        if (razaActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar la raza. Verifique que no esté repetida y que la especie exista.");
        }

        return ResponseEntity.ok(razaActualizada);
    }
    // Elimina la raza solo si existe
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRaza(@PathVariable Integer id) {
        boolean eliminado = razaService.eliminarRaza(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
