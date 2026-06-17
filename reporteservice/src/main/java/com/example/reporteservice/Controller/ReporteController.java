package com.example.reporteservice.Controller;

import com.example.reporteservice.Model.Reporte;
import com.example.reporteservice.Service.ReporteService;
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
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "API para la gestion de reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(
            summary = "Listar reportes",
            description = "Obtiene la lista de todos los reportes registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reportes encontrados con exito"),
            @ApiResponse(responseCode = "204", description = "No hay reportes dentro del sistema")
    })
    public ResponseEntity<List<Reporte>> getReportes() {
        List<Reporte> reportes = reporteService.listarReportes();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    @PostMapping
    @Operation(
            summary = "Crear reporte",
            description = "Registra reportes dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Este reporte ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "reporte creado con exito")
    })
    public ResponseEntity<?> createReporte(@Valid @RequestBody Reporte reporte) {
        Reporte nuevoReporte = reporteService.guardarReporte(reporte);

        if (nuevoReporte == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el reporte. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar reporte",
            description = "Buscar un reporte por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "200", description = "Reporte encontrado dentro del sistema")
    })
    public ResponseEntity<Reporte> getReporteById(@Parameter(description = "ID del reporte", example = "1")
                                                  @PathVariable Integer id) {
        Reporte reporte = reporteService.buscarPorId(id);

        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reporte);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar reporte",
            description = "Actualiza el reporte por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "200", description = "Reporte actualizado con exito")
    })
    public ResponseEntity<?> updateReporte(@Parameter(description = "ID del reporte a Actualizar", example = "1")
                                           @PathVariable Integer id, @Valid @RequestBody Reporte reporte) {
        Reporte reporteActualizado = reporteService.actualizarReporte(id, reporte);

        if (reporteActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el reporte. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(reporteActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar reporte",
            description = "Elimina el reporte asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado"),
            @ApiResponse(responseCode = "200", description = "Reporte eliminado con exito")
    })
    public ResponseEntity<String> deleteReporte(@Parameter(description = "ID del reporte a eliminar", example = "1")
                                                @PathVariable Integer id) {
        boolean eliminado = reporteService.eliminarReporte(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte no encontrado.");
        }

        return ResponseEntity.ok("Reporte eliminado correctamente.");
    }
}
