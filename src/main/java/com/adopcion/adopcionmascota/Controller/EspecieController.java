package com.adopcion.adopcionmascota.Controller;


import com.adopcion.adopcionmascota.Model.Especie;
import com.adopcion.adopcionmascota.Service.EspecieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/especies")
public class EspecieController {
    @Autowired
    private EspecieService especieService;

    @GetMapping
    public ResponseEntity<List<Especie>> getEspecies() {
        List<Especie> especies = especieService.listarEspecies();

        if (especies.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 no content
        }

        return ResponseEntity.ok(especies); // 200 OK

    }

    @PostMapping
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
    public ResponseEntity<Especie> getId(@PathVariable Integer id) {
        Especie especie = especieService.buscarPorId(id);

        if (especie == null) {
            return ResponseEntity.notFound().build(); //200

        }
        return ResponseEntity.ok(especie);
    }
        @PutMapping("/{id}")
        public ResponseEntity<Especie> updateEspecie (@PathVariable Integer id, @Valid @RequestBody Especie especie){
            Especie especieActualizada = especieService.actualizarEspecie(id, especie);
            if (especieActualizada == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(especieActualizada);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteEspecie (@PathVariable Integer id){
            boolean eliminado = especieService.eliminarEspecie(id);


            if (!eliminado) {
                return ResponseEntity.notFound().build(); //404
            }
            return ResponseEntity.ok("Se eliminó la especie correctamente"); //200
        }
    }






