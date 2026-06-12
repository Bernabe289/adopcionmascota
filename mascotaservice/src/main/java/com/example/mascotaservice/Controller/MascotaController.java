package com.example.mascotaservice.Controller;

import com.example.mascotaservice.Model.Mascota;
import com.example.mascotaservice.Service.MascotaService;
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
@RequestMapping("/api/v1/mascotas")
@Tag(name= "Mascotas", description = "API para la gestion de mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    @Operation(
            summary = "Listar mascotas",
            description = "Obtiene la lista de todas las mascotas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotas encontradas con éxito"),
            @ApiResponse(responseCode = "404", description = "No existen mascotas registradas")
    })

    public ResponseEntity<List<Mascota>> getMascotas() {
        List<Mascota> mascotas = mascotaService.listarMascotas();

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mascotas);
    }

    @PostMapping
    @Operation(
            summary = "Crear Mascota",
            description = "Registra una mascota en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mascota creada con exito"),
            @ApiResponse(responseCode = "409", description = "La mascota ya existe o hay conflicto con los datos ingresados")
    })
    public ResponseEntity<?> createMascota(@Valid @RequestBody Mascota mascota) {
        Mascota nuevaMascota = mascotaService.guardarMascota(mascota);

        if (nuevaMascota == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la mascota. Verifique que tenga raza y refugio.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar mascota",
            description = "Busca una mascota por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota encontrada con exito"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada en el sistema")

    })
    public ResponseEntity<Mascota> getId(@Parameter(description = "ID de la mascota", example = "1")
                                             @PathVariable Integer id) {
        Mascota mascota = mascotaService.buscarPorId(id);

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mascota);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar mascota",
            description = "Actualiza la mascota por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota actualizada con exito"),
            @ApiResponse(responseCode = "409", description = "La mascota no se pudo actualizar. Hay conflicto con los datos ingresados")

    })
    public ResponseEntity<?> updateMascota(@Parameter(description = "ID de la mascota a actualizar", example = "1")
                                               @PathVariable Integer id, @Valid @RequestBody Mascota mascota) {
        Mascota mascotaExistente = mascotaService.buscarPorId(id);

        if (mascotaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Mascota mascotaActualizada = mascotaService.actualizarMascota(id, mascota);

        if (mascotaActualizada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la mascota. Verifique que tenga raza y refugio.");
        }

        return ResponseEntity.ok(mascotaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar mascota",
            description = "Elimina una mascota por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada en el sistema"),
            @ApiResponse(responseCode = "200", description = "Mascota eliminada con exito del sistema")

    })
    public ResponseEntity<String> deleteMascota(@Parameter(description = "ID de la mascota a eliminar", example = "1")
                                                    @PathVariable Integer id) {
        boolean eliminado = mascotaService.eliminarMascota(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }


}