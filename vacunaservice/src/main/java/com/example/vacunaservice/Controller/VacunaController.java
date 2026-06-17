package com.example.vacunaservice.Controller;

import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Service.VacunaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Service.VacunaService;
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
@RequestMapping("/api/v1/vacunas")
@Tag(name = "Vacuna Service", description = "API para la gestion de vacunas")
public class VacunaController {

    @Autowired
    private VacunaService vacunaService;

    @GetMapping
    @Operation(
            summary = "Listar vacunas",
            description = "Obtiene la lista de todas las vacunas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vacunas encontradas con exito"),
            @ApiResponse(responseCode = "204", description = "No hay vacunas dentro del sistema")
    })
    public ResponseEntity<List<Vacuna>> getVacunas() {
        List<Vacuna> vacunas = vacunaService.listarVacunas();

        if (vacunas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(vacunas);
    }

    @PostMapping
    @Operation(
            summary = "Crear vacuna",
            description = "Registra vacunas dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Esta vacuna ya se encuentra en el sistema"),
            @ApiResponse(responseCode = "201", description = "Vacuna creada con exito")

    })
    public ResponseEntity<?> createVacuna(@Valid @RequestBody Vacuna vacuna) {
        Vacuna nuevaVacuna = vacunaService.guardarVacuna(vacuna);

        if (nuevaVacuna == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la vacuna. Verifique que tenga un historial asociado.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVacuna);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar vacuna",
            description = "Buscar vacuna por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Vacuna no encontrada"),
            @ApiResponse(responseCode = "200", description = "Vacuna encontrada dentro del sistema")
    })
    public ResponseEntity<Vacuna> getId(@Parameter(description = "ID de la vacuna", example = "1")
                                        @PathVariable Integer id) {
        Vacuna vacuna = vacunaService.buscarPorId(id);

        if (vacuna == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(vacuna);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar vacuna",
            description = "Actualiza una vacuna por su id asociada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Vacuna no encontrada"),
            @ApiResponse(responseCode = "200", description = "Vacuna actualizada con exito")
    })
    public ResponseEntity<?> updateVacuna(@Parameter(description = "ID de la vacuna a Actualizar", example = "1")
                                          @PathVariable Integer id, @Valid @RequestBody Vacuna vacuna) {
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
    @Operation(
            summary = "Eliminar vacuna",
            description = "Elimina una vacuna por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Vacuna no encontrada"),
            @ApiResponse(responseCode = "200", description = "Vacuna eliminada con exito")

    })
    public ResponseEntity<String> deleteVacuna(@Parameter(description = "ID de la vacuna a Eliminar", example = "1")
                                               @PathVariable Integer id) {
        boolean eliminado = vacunaService.eliminarVacuna(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}