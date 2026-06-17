package com.example.visitaservice.Controller;

import com.example.visitaservice.Model.Visita;
import com.example.visitaservice.Service.VisitaService;
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
@RequestMapping("/api/v1/visitas")
@Tag(name = "Visita ", description = "API para la gestion de visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @GetMapping
    @Operation(
            summary = "Listar visitas",
            description = "Obtiene la lista de todas las visitas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Visitas encontradas con exito"),
            @ApiResponse(responseCode = "204", description = "No hay visitas dentro del sistema")
    })
    public ResponseEntity<List<Visita>> getVisitas() {
        List<Visita> visitas = visitaService.listarVisitas();

        if (visitas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(visitas);
    }

    @PostMapping
    @Operation(
            summary = "Crear visita",
            description = "Registra visitas dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Esta visita ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "Visita creada con exito")
    })
    public ResponseEntity<?> createVisita(@Valid @RequestBody Visita visita) {
        Visita nuevaVisita = visitaService.guardarVisita(visita);

        if (nuevaVisita == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la visita. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVisita);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar visita",
            description = "Buscar una visita por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Visita no encontrada"),
            @ApiResponse(responseCode = "200", description = "Visita encontrada dentro del sistema")
    })
    public ResponseEntity<Visita> getId(@Parameter(description = "ID de la visita", example = "1")
                                        @PathVariable Integer id) {
        Visita visita = visitaService.buscarPorId(id);

        if (visita == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(visita);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar visita",
            description = "Actualiza la visita por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Visita no encontrada"),
            @ApiResponse(responseCode = "200", description = "Visita actualizada con exito")
    })
    public ResponseEntity<?> updateVisita(@Parameter(description = "ID de la visita a Actualizar", example = "1")
                                          @PathVariable Integer id, @Valid @RequestBody Visita visita) {
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
    @Operation(
            summary = "Eliminar visita",
            description = "Elimina una visita asociada por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Visita no encontrado"),
            @ApiResponse(responseCode = "200", description = "Visita eliminado con exito")
    })
    public ResponseEntity<String> deleteVisita(@Parameter(description = "ID de la visita a Eliminar", example = "1")
                                               @PathVariable Integer id) {
        boolean eliminado = visitaService.eliminarVisita(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}