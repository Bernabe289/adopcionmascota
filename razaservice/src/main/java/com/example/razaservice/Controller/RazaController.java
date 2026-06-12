package com.example.razaservice.Controller;

import com.example.razaservice.Model.Raza;
import com.example.razaservice.Service.RazaService;
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
@RequestMapping("/api/v1/razas")
@Tag(name = "Razas", description = "API para la gestión de razas")
public class RazaController {

    @Autowired
    private RazaService razaService;

    @GetMapping
    @Operation(
            summary = "Listar razas",
            description = "Obtiene la lista de todos las razas registradas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No existen razas registradas"),
            @ApiResponse(responseCode = "200", description = "Razas encontradas con exito")
    })
    public ResponseEntity<List<Raza>> getRazas() {
        List<Raza> razas = razaService.listarRazas();

        if (razas.isEmpty()) {
            return ResponseEntity.noContent().build(); //204
        }

        return ResponseEntity.ok(razas); //200
    }

    @PostMapping// Valida que la raza no esté repetida y que la especie exista
    @Operation(
            summary = "Crear raza",
            description = "Registra raza en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Datos invalidos o raza no existente"),
            @ApiResponse(responseCode = "201", description = "Raza creada con exito")
    })
    public ResponseEntity<?> createRaza(@Valid @RequestBody Raza raza) {
        Raza nuevaRaza = razaService.guardarRaza(raza);

        if (nuevaRaza == null) { //400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar la raza. Verifique que no esté repetida y que la especie exista.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaRaza); //201
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar raza",
            description = "Busca una raza por su id asociado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Raza no encontrada"),
            @ApiResponse(responseCode = "200", description = "Raza encontradas con exito")
    })
    public ResponseEntity<Raza> getId(@Parameter(description = "ID de la raza", example = "1")
                                          @PathVariable Integer id) {
        Raza raza = razaService.buscarPorId(id);

        if (raza == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(raza);
    }

    @PutMapping("/{id}") // Actualiza solo si la raza existe, no está duplicada y tiene una especie válida
    @Operation(
            summary = "Actualizar raza",
            description = "Actualiza los datos de una raza por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Raza no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o raza no existente"),
            @ApiResponse(responseCode = "200", description = "Raza actualizada con exito")
    })
    public ResponseEntity<?> updateRaza(@Parameter(description = "ID de la raza a actualizar", example = "1")
                                            @PathVariable Integer id, @Valid @RequestBody Raza raza) {
        Raza razaExistente = razaService.buscarPorId(id);

        if (razaExistente == null) {
            return ResponseEntity.notFound().build(); //404
        }

        Raza razaActualizada = razaService.actualizarRaza(id, raza);

        if (razaActualizada == null) { //400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar la raza. Verifique que no esté repetida y que la especie exista.");
        }

        return ResponseEntity.ok(razaActualizada); //200
    }
    // Elimina la raza solo si existe
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar raza",
            description = "Elimina una raza existente por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Raza no encontrada"),
            @ApiResponse(responseCode = "200", description = "Raza eliminada con exito")
    })
    public ResponseEntity<String> deleteRaza(@Parameter(description = "ID de la raza a eliminar", example = "1")
                                                 @PathVariable Integer id) {
        boolean eliminado = razaService.eliminarRaza(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}
