package com.example.refugioservice.Controller;

import com.example.refugioservice.Model.Refugio;
import com.example.refugioservice.Service.RefugioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/refugios")
public class RefugioController {
    @Autowired
    private RefugioService refugioService;

    @GetMapping
    public ResponseEntity<List<Refugio>> getRefugios(){
        List<Refugio> refugios = refugioService.listarRefugios();

        if(refugios.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(refugios);
    }

    @PostMapping
    public ResponseEntity<?> createRefugio(@Valid @RequestBody Refugio refugio) {
        Refugio nuevoRefugio = refugioService.guardarRefugio(refugio);

        if (nuevoRefugio == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se pudo crear el refugio. Verifique que el email no esté repetido.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRefugio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRefugio(@PathVariable Integer id, @Valid @RequestBody Refugio refugio) {
        Refugio refugioActualizado = refugioService.actualizarRefugio(id, refugio);

        if (refugioActualizado == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se pudo actualizar el refugio. Verifique que exista y que el email no esté repetido.");
        }

        return ResponseEntity.ok(refugioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRefugio(@PathVariable Integer id) {
        boolean eliminado = refugioService.eliminarRefugio(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refugio no encontrado.");
        }

        return ResponseEntity.ok("Refugio eliminado correctamente.");
    }


}
