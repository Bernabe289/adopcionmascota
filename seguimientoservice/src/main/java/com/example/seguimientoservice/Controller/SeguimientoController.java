package com.example.seguimientoservice.Controller;

import com.example.seguimientoservice.Model.Seguimiento;
import com.example.seguimientoservice.Service.SeguimientoService;
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
@RequestMapping("/api/v1/seguimientos")
@Tag(name = "Rol Usuario", description = "API para la gestion de seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping
    @Operation(
            summary = "Listar seguimiento",
            description = "Obtiene la lista de todos los seguimientos registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguimientos encontrados con exito"),
            @ApiResponse(responseCode = "204", description = "No hay seguimientos dentro del sistema")
    })
    public ResponseEntity<List<Seguimiento>> getSeguimientos() {
        List<Seguimiento> seguimientos = seguimientoService.listarSeguimientos();

        if (seguimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(seguimientos);
    }

    @PostMapping
    @Operation(
            summary = "Crear seguimiento",
            description = "Registra seguimientos dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Este seguimiento ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "Seguimiento creado con exito")
    })
    public ResponseEntity<?> createSeguimiento(@Valid @RequestBody Seguimiento seguimiento) {
        Seguimiento nuevoSeguimiento = seguimientoService.guardarSeguimiento(seguimiento);

        if (nuevoSeguimiento == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar el seguimiento. Verifique que tenga una solicitud asociada.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSeguimiento);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar seguimiento",
            description = "Buscar un seguimiento por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Seguimiento encontrado dentro del sistema")
    })
    public ResponseEntity<Seguimiento> getId(@Parameter(description = "ID del seguimiento", example = "1")
                                             @PathVariable Integer id) {
        Seguimiento seguimiento = seguimientoService.buscarPorId(id);

        if (seguimiento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(seguimiento);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar seguimiento",
            description = "Actualiza el seguimiento por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Seguimiento actualizado con exito")
    })
    public ResponseEntity<?> updateSeguimiento(@Parameter(description = "ID del seguimiento a Actualizar", example = "1")
                                               @PathVariable Integer id, @Valid @RequestBody Seguimiento seguimiento) {
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
    @Operation(
            summary = "Eliminar seguimiento",
            description = "Elimina el seguimiento asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Seguimiento eliminado con exito")
    })
    public ResponseEntity<String> deleteSeguimiento(@Parameter(description = "ID del seguimiento a Eliminar", example = "1")
                                                    @PathVariable Integer id) {
        boolean eliminado = seguimientoService.eliminarSeguimiento(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
