package com.example.solicitudservice.Controller;

import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Service.SolicitudAdopcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/solicitudes")
@Tag(name = "Solicitudes", description = "API para la gestion de solicitudes de mascotas")
public class SolicitudAdopcionController {

    @Autowired
    private SolicitudAdopcionService solicitudAdopcionService;

    @GetMapping
    @Operation(
            summary = "Listar Solicitudes",
            description = "Obtiene la lista de todas las solicitudes del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes encontradas con exito"),
            @ApiResponse(responseCode = "204", description = "No existen solicitudes registradas")
    })

    public ResponseEntity<List<SolicitudAdopcion>> getSolicitudes() {
        List<SolicitudAdopcion> solicitudes = solicitudAdopcionService.listarSolicitudes();

        if (solicitudes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping
    @Operation(
            summary = "Crear solicitud",
            description = "Registra una solicitud en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada con exito"),
            @ApiResponse(responseCode = "409", description = "La solicitud ya existe")
    })
    public ResponseEntity<?> createSolicitud(@Valid @RequestBody SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion nuevaSolicitud = solicitudAdopcionService.guardarSolicitud(solicitudAdopcion);

        if (nuevaSolicitud == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la solicitud. Verifique usuario, mascota y datos obligatorios.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar solicitud",
            description = "Buscar una solicitud por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada con exito"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada en el sistema")
    })
    public ResponseEntity<SolicitudAdopcion> getSolicitudById(@Parameter(description= "ID de solicitud", example = "1")
                                                                  @PathVariable Integer id) {
        SolicitudAdopcion solicitudAdopcion = solicitudAdopcionService.buscarPorId(id);

        if (solicitudAdopcion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(solicitudAdopcion);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar solicitud",
            description = "Actualizar solicitud por su id asociada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada"),
            @ApiResponse(responseCode = "409", description = "La solicitud no se pudo actualizar")
    })
    public ResponseEntity<?> updateSolicitud(@Parameter(description= "ID de la solicitud a actualizar", example = "1")
                                                 @PathVariable Integer id, @Valid @RequestBody SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion solicitudActualizada = solicitudAdopcionService.actualizarSolicitud(id, solicitudAdopcion);

        if (solicitudActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la solicitud. Verifique que exista y/o que los datos sean correctos.");
        }

        return ResponseEntity.ok(solicitudActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar solicitud",
            description = "Elimina una solicitud por su id asociada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "La solicitud no ha sido encontrada en el sistema"),
            @ApiResponse(responseCode = "200", description = "Solicitud eliminada con exito")
    })
    public ResponseEntity<String> deleteSolicitud(@Parameter(description = "ID del usuario a eliminar", example = "1")
                                                      @PathVariable Integer id) {
        boolean eliminado = solicitudAdopcionService.eliminarSolicitud(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada.");
        }

        return ResponseEntity.ok("Solicitud eliminada correctamente.");
    }
}
