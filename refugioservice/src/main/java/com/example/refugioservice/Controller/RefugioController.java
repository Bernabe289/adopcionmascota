package com.example.refugioservice.Controller;

import com.example.refugioservice.Model.Refugio;
import com.example.refugioservice.Service.RefugioService;
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
@RequestMapping("/api/v1/refugios")
@Tag(name = "Refugios", description = "API para la gestion de refugios")
public class RefugioController {
    @Autowired
    private RefugioService refugioService;

    @GetMapping
    @Operation(
            summary = "Listar refugios",
            description = "Obtiene la lista de todos los refugios registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No existen refugios registrados"),
            @ApiResponse(responseCode = "200", description = "Refugios encontrados con exito")
    })
    public ResponseEntity<List<Refugio>> getRefugios(){
        List<Refugio> refugios = refugioService.listarRefugios();

        if(refugios.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }

        return ResponseEntity.ok(refugios); //200
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar refugio",
            description = "Busca refugio asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Refugio no encontrado."),
            @ApiResponse(responseCode = "200", description = "Refugio encontrado con exito")
    })
    public ResponseEntity<Refugio> getRefugioById(@PathVariable Integer id) {
        Refugio refugio = refugioService.buscarPorId(id);

        if (refugio == null) {
            return ResponseEntity.notFound().build(); //404
        }

        return ResponseEntity.ok(refugio); //200
    }

    @PostMapping
    @Operation(
            summary = "Crear refugio",
            description = "Registra refugio dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Refugio no encontrado."),
            @ApiResponse(responseCode = "200", description = "Refugio encontrado con exito")
    })
    public ResponseEntity<?> createRefugio(@Valid @RequestBody Refugio refugio) {
        Refugio nuevoRefugio = refugioService.guardarRefugio(refugio);

        if (nuevoRefugio == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT) //409
                    .body("No se pudo crear el refugio. Verifique que el email no esté repetido.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRefugio); //201
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar refugio",
            description = "Actualiza el refugio asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Refugio no encontrado, no es posible actualizar."),
            @ApiResponse(responseCode = "200", description = "Refugio actualizado con exito")
    })
    public ResponseEntity<?> updateRefugio(@PathVariable Integer id, @Valid @RequestBody Refugio refugio) {
        Refugio refugioActualizado = refugioService.actualizarRefugio(id, refugio);

        if (refugioActualizado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT) //409
                    .body("No se pudo actualizar el refugio. Verifique que exista y que el email no esté repetido.");
        }

        return ResponseEntity.ok(refugioActualizado); //200
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar refugio",
            description = "Eliminar refugio asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Refugio no encontrado, no es posible eliminar."),
            @ApiResponse(responseCode = "200", description = "Refugio eliminado con exito")
    })
    public ResponseEntity<String> deleteRefugio(@PathVariable Integer id) {
        boolean eliminado = refugioService.eliminarRefugio(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refugio no encontrado."); //409
        }

        return ResponseEntity.ok("Refugio eliminado correctamente."); //200
    }


}
