package com.example.historialvetservice.Controller;

import com.example.historialvetservice.Model.HistorialVet;
import com.example.historialvetservice.Service.HistorialVetService;
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

import java.nio.channels.NotYetBoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/historiales")
@Tag(name = "Historial Veterinario", description = "API para la gestion de historial veterinario")
public class HistorialVetController {

    @Autowired
    private HistorialVetService historialVetService;

    @GetMapping
    @Operation(
            summary = "Listar Historial",
            description = "Obtiene la lista de todos los historiales registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historiales encontrados"),
            @ApiResponse(responseCode = "204", description = "No hay historiales en el sistema")
    })
    public ResponseEntity<List<HistorialVet>> getHistoriales() {
        List<HistorialVet> historiales = historialVetService.listarHistoriales();

        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(historiales);
    }

    @PostMapping
    @Operation(
            summary = "Crear historial",
            description = "Registra un historial dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Este historial ya se encuentra en el sistema"),
            @ApiResponse(responseCode = "201", description = "Historial creado con exito")
    })
    public ResponseEntity<?> createHistorial(@Valid @RequestBody HistorialVet historialVet) {
        HistorialVet nuevoHistorial = historialVetService.guardarHistorial(historialVet);

        if (nuevoHistorial == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar el historial. Verifique que tenga una mascota asignada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHistorial);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar historial",
            description = "Buscar historial por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
            @ApiResponse(responseCode = "200", description = "Historial encontrado")
    })
    public ResponseEntity<HistorialVet> getId(@Parameter(description = "ID del historial", example = "1")
                                              @PathVariable Integer id) {
        HistorialVet historialVet = historialVetService.buscarPorId(id);

        if (historialVet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(historialVet);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar historial",
            description = "Actualiza el historial por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
            @ApiResponse(responseCode = "404", description = "Historial actualizado con exito")
    })
    public ResponseEntity<?> updateHistorial(@Parameter(description = "ID del historial a Actualizar", example = "1")
                                             @PathVariable Integer id, @Valid @RequestBody HistorialVet historialVet) {
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
    @Operation(
            summary = "Eliminar historial",
            description = "Elimina el historial asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Historial no encontrado"),
            @ApiResponse(responseCode = "200", description = "Historial eliminado con exito")
    })
    public ResponseEntity<String> deleteHistorial(@Parameter(description = "ID del historial a Eliminar", example = "1")
                                                  @PathVariable Integer id) {
        boolean eliminado = historialVetService.eliminarHistorial(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}