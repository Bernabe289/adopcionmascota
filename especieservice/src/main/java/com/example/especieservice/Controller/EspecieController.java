package com.example.especieservice.Controller;


import com.example.especieservice.Model.Especie;
import com.example.especieservice.Service.EspecieService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/especies")
@Tag(name = "Especie", description = "API para la gestion de especies")
public class EspecieController {
    @Autowired
    private EspecieService especieService;

    @GetMapping
    @Operation(
            summary = "Obtener especies",
            description = "Obtiene la lista de todas las especies registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No hay especies dentro del sistema"),
            @ApiResponse(responseCode = "200", description = "Especie encontrada con exito")
    })
    public ResponseEntity<List<Especie>> getEspecies() {
        List<Especie> especies = especieService.listarEspecies();

        if (especies.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 no content
        }

        return ResponseEntity.ok(especies); // 200 OK

    }

    @PostMapping
    @Operation(
            summary = "Crear especie",
            description = "Registra especie dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "La especie ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "Especie creada con exito")
    })
    public ResponseEntity<?> createEspecie(@Valid @RequestBody Especie especie){
        Especie nuevaEspecie = especieService.guardarEspecie(especie);

        if(nuevaEspecie == null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("La especie ya existe"); // 409
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevaEspecie); //201
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar especie",
            description = "Obtiene especie por el id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Error, no se pudo encontrar la especie"),
            @ApiResponse(responseCode = "200", description = "Especie encontrada con ecxito")
    })
    public ResponseEntity<Especie> getId(@PathVariable Integer id) {
        Especie especie = especieService.buscarPorId(id);

        if (especie == null) {
            return ResponseEntity.notFound().build(); //409

        }
        return ResponseEntity.ok(especie); //200
    }
        @PutMapping("/{id}")
        @Operation(
                summary = "Actualizar especie",
                description = "Actualizar especie por su id asociado"
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "404", description = "Error, especie no encontrada"),
                @ApiResponse(responseCode = "200", description = "Especie actualizada con exito")
        })
        public ResponseEntity<Especie> updateEspecie (@PathVariable Integer id, @Valid @RequestBody Especie especie){
            Especie especieActualizada = especieService.actualizarEspecie(id, especie);
            if (especieActualizada == null) {
                return ResponseEntity.notFound().build(); //404
            }
            return ResponseEntity.ok(especieActualizada); //200
        }

        @DeleteMapping("/{id}")
        @Operation(
                summary = "Eliminar especie",
                description = "Se elimina especie por su id asociado"
        )
        @ApiResponses(value = {
                @ApiResponse(responseCode = "404", description = "Error, no se encontro la especie"),
                @ApiResponse(responseCode = "200", description = "Especie eliminada con exito")
        })
        public ResponseEntity<String> deleteEspecie (@PathVariable Integer id){
            boolean eliminado = especieService.eliminarEspecie(id);


            if (!eliminado) {
                return ResponseEntity.notFound().build(); //404
            }
            return ResponseEntity.ok("Se eliminó la especie correctamente"); //200
        }
    }






