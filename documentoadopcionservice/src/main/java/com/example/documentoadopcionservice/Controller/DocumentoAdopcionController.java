package com.example.documentoadopcionservice.Controller;

import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import com.example.documentoadopcionservice.Service.DocumentoAdopcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoAdopcionController {
    @Autowired
    private DocumentoAdopcionService documentoAdopcionService;

    @GetMapping
    public ResponseEntity<List<DocumentoAdopcion>> getDocumentos() {
        List<DocumentoAdopcion> documentos = documentoAdopcionService.listarDocumentos();

        if (documentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documentos);
    }

    @PostMapping
    public ResponseEntity<?> createDocumento(@Valid @RequestBody DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion nuevoDocumento = documentoAdopcionService.guardarDocumento(documentoAdopcion);

        if (nuevoDocumento == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el documento. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDocumento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoAdopcion> getDocumentoById(@PathVariable Integer id) {
        DocumentoAdopcion documentoAdopcion = documentoAdopcionService.buscarPorId(id);

        if (documentoAdopcion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(documentoAdopcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocumento(@PathVariable Integer id, @Valid @RequestBody DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion documentoActualizado = documentoAdopcionService.actualizarDocumento(id, documentoAdopcion);

        if (documentoActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el documento. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(documentoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocumento(@PathVariable Integer id) {
        boolean eliminado = documentoAdopcionService.eliminarDocumento(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado.");
        }

        return ResponseEntity.ok("Documento eliminado correctamente.");
    }

}