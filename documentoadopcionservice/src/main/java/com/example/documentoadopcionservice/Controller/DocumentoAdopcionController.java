package com.example.documentoadopcionservice.Controller;

import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import com.example.documentoadopcionservice.Service.DocumentoAdopcionService;
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
@RequestMapping("/api/v1/documentos")
@Tag(name = "Documento Adopción", description = "API para la gestión de documentos de adopción")
public class DocumentoAdopcionController {
    @Autowired
    private DocumentoAdopcionService documentoAdopcionService;

    @GetMapping
    @Operation(
            summary = "Listar documentos",
            description = "Obtiene la lista de todos los documentos de adopción registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos encontrados con exito"),
            @ApiResponse(responseCode = "204", description = "No hay documentos registrados en el sistema")
    })
    public ResponseEntity<List<DocumentoAdopcion>> getDocumentos() {
        List<DocumentoAdopcion> documentos = documentoAdopcionService.listarDocumentos();

        if (documentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documentos);
    }

    @PostMapping
    @Operation(
            summary = "Crear documento",
            description = "Registra documentos dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Este documento ya se encuentra en el sistema"),
            @ApiResponse(responseCode = "201", description = "Documento creado con exito")
    })
    public ResponseEntity<?> createDocumento(@Valid @RequestBody DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion nuevoDocumento = documentoAdopcionService.guardarDocumento(documentoAdopcion);

        if (nuevoDocumento == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el documento. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDocumento);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar documento",
            description = "Buscar un documento por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Documento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Documento encontrado en el sistema")
    })
    public ResponseEntity<DocumentoAdopcion> getDocumentoById(@Parameter(description = "ID del documento", example = "1")
                                                              @PathVariable Integer id) {
        DocumentoAdopcion documentoAdopcion = documentoAdopcionService.buscarPorId(id);

        if (documentoAdopcion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(documentoAdopcion);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar documento",
            description = "Actualizar un documento por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Documento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Documento actualizado con exito")
    })
    public ResponseEntity<?> updateDocumento(@Parameter(description = "ID del documento a Actualizar", example = "1")
                                             @PathVariable Integer id, @Valid @RequestBody DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion documentoActualizado = documentoAdopcionService.actualizarDocumento(id, documentoAdopcion);

        if (documentoActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el documento. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(documentoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar documento",
            description = "Elimina el documento asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Documento no encontrado"),
            @ApiResponse(responseCode = "200", description = "Documento eliminado con exito")
    })
    public ResponseEntity<String> deleteDocumento(@Parameter(description = "ID del documento a Eliminar", example = "1")
                                                  @PathVariable Integer id) {
        boolean eliminado = documentoAdopcionService.eliminarDocumento(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado.");
        }

        return ResponseEntity.ok("Documento eliminado correctamente.");
    }

}