package com.example.mascotaservice.Controller;

import com.example.mascotaservice.Model.Mascota;
import com.example.mascotaservice.Service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> getMascotas() {
        List<Mascota> mascotas = mascotaService.listarMascotas();

        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mascotas);
    }

    @PostMapping
    public ResponseEntity<?> createMascota(@Valid @RequestBody Mascota mascota) {
        Mascota nuevaMascota = mascotaService.guardarMascota(mascota);

        if (nuevaMascota == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo guardar la mascota. Verifique que tenga raza y refugio.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMascota);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getId(@PathVariable Integer id) {
        Mascota mascota = mascotaService.buscarPorId(id);

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMascota(@PathVariable Integer id, @Valid @RequestBody Mascota mascota) {
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
    public ResponseEntity<String> deleteMascota(@PathVariable Integer id) {
        boolean eliminado = mascotaService.eliminarMascota(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Se eliminó correctamente.");
    }
}