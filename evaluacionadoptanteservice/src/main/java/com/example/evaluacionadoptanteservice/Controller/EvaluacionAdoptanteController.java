package com.example.evaluacionadoptanteservice.Controller;

import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import com.example.evaluacionadoptanteservice.Service.EvaluacionAdoptanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionAdoptanteController {
    @Autowired
    private EvaluacionAdoptanteService evaluacionAdoptanteService;

    @GetMapping
    public ResponseEntity<List<EvaluacionAdoptante>> getEvaluaciones() {
        List<EvaluacionAdoptante> evaluaciones = evaluacionAdoptanteService.listarEvaluaciones();

        if (evaluaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(evaluaciones);
    }

    @PostMapping
    public ResponseEntity<?> createEvaluacion(@Valid @RequestBody EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante nuevaEvaluacion = evaluacionAdoptanteService.guardarEvaluacion(evaluacionAdoptante);

        if (nuevaEvaluacion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la evaluación. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEvaluacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionAdoptante> getEvaluacionById(@PathVariable Integer id) {
        EvaluacionAdoptante evaluacionAdoptante = evaluacionAdoptanteService.buscarPorId(id);

        if (evaluacionAdoptante == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evaluacionAdoptante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvaluacion(@PathVariable Integer id, @Valid @RequestBody EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante evaluacionActualizada = evaluacionAdoptanteService.actualizarEvaluacion(id, evaluacionAdoptante);

        if (evaluacionActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la evaluación. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(evaluacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvaluacion(@PathVariable Integer id) {
        boolean eliminado = evaluacionAdoptanteService.eliminarEvaluacion(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluación no encontrada.");
        }

        return ResponseEntity.ok("Evaluación eliminada correctamente.");
    }
}
