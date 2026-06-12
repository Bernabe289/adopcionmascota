package com.example.evaluacionadoptanteservice.Controller;

import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import com.example.evaluacionadoptanteservice.Service.EvaluacionAdoptanteService;
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
@RequestMapping("/api/v1/evaluaciones")
@Tag(name = "Evaluacion Adoptante", description = "API para la gestión de Evaluaciones adoptante de mascotas")
public class EvaluacionAdoptanteController {
    @Autowired
    private EvaluacionAdoptanteService evaluacionAdoptanteService;

    @GetMapping
    @Operation(
            summary = "Listar evaluaciones",
            description = "Obtiene la lista de todas las evaluaciones del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No hay evaluaciones registradas dentro del sistema"),
            @ApiResponse(responseCode = "200", description = "Evaluaciones encontradas con exito")
    })
    public ResponseEntity<List<EvaluacionAdoptante>> getEvaluaciones() {
        List<EvaluacionAdoptante> evaluaciones = evaluacionAdoptanteService.listarEvaluaciones();

        if (evaluaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(evaluaciones);
    }

    @PostMapping
    @Operation(
            summary = "Crear Evaluacion",
            description = "Registra una evaluacion en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Datos invalidos, no se puedo crear la evaluacion."),
            @ApiResponse(responseCode = "201", description = "Evaluacion creada con exito.")
    })
    public ResponseEntity<?> createEvaluacion(@Valid @RequestBody EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante nuevaEvaluacion = evaluacionAdoptanteService.guardarEvaluacion(evaluacionAdoptante);

        if (nuevaEvaluacion == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) //400
                    .body("No se pudo crear la evaluación. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEvaluacion); //201
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar evaluacion",
            description = "Busca evaluacion mediante su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada en el sistema."),
            @ApiResponse(responseCode = "200", description = "Evaluacion encontrada con exito.")
    })
    public ResponseEntity<EvaluacionAdoptante> getEvaluacionById(@Parameter(description = "ID de la evaluacion", example = "1")
                                                                     @PathVariable Integer id) {
        EvaluacionAdoptante evaluacionAdoptante = evaluacionAdoptanteService.buscarPorId(id);

        if (evaluacionAdoptante == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evaluacionAdoptante);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar evaluacion",
            description = "Actualiza evaluacion mediante su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Datos invalidos o no existe, no es posible seguir con la operacion."),
            @ApiResponse(responseCode = "200", description = "Evaluacion actualizada con exito.")
    })
    public ResponseEntity<?> updateEvaluacion(@Parameter(description = "ID de la evaluacion a actualizar", example = "1")
                                                  @PathVariable Integer id, @Valid @RequestBody EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante evaluacionActualizada = evaluacionAdoptanteService.actualizarEvaluacion(id, evaluacionAdoptante);

        if (evaluacionActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) //400
                    .body("No se pudo actualizar la evaluación. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(evaluacionActualizada); //200
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar evaluacion",
            description = "Elimina evaluacion mediante su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Evaluacion no encontrada en el sistema."),
            @ApiResponse(responseCode = "200", description = "Evaluacion eliminada con exito.")
    })
    public ResponseEntity<String> deleteEvaluacion(@Parameter(description = "ID de la evaluacion a eliminar", example = "1")
                                                       @PathVariable Integer id) {
        boolean eliminado = evaluacionAdoptanteService.eliminarEvaluacion(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evaluación no encontrada.");
        }

        return ResponseEntity.ok("Evaluación eliminada correctamente.");
    }
}
