package com.example.rolservice.Controller;

import com.example.rolservice.Model.RolUsuario;
import com.example.rolservice.Service.RolUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RolUsuarioController {

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @GetMapping
    public ResponseEntity<List<RolUsuario>> getRol(){
        List<RolUsuario> roles = rolUsuarioService.listarRoles();

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 No contenido
        }

        return ResponseEntity.ok(roles); //200 OK
    }

    @PostMapping
    public ResponseEntity<?> createRol(@Valid @RequestBody RolUsuario rolUsuario){
        RolUsuario nuevoRol = rolUsuarioService.guardarRol(rolUsuario);

        if (nuevoRol == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El rol ya existe."); //ERROR 409 ya existe el contenido
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol); //201 CREADO
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolUsuario> getId(@PathVariable Integer id){
        RolUsuario rolUsuario = rolUsuarioService.buscarPorId(id);

        if (rolUsuario == null){
            return ResponseEntity.notFound().build(); //Error 404 NOT FOUND
        }

        return ResponseEntity.ok(rolUsuario); // 200 OK
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolUsuario> updateRol(@PathVariable Integer id, @Valid @RequestBody RolUsuario rolUsuario){
        RolUsuario rolActualizado = rolUsuarioService.actualizarRol(id, rolUsuario);

        if (rolActualizado == null){
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }

        return ResponseEntity.ok(rolActualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRol(@PathVariable Integer id){
        boolean eliminado = rolUsuarioService.eliminarRol(id);

                if(!eliminado){
                    return ResponseEntity.notFound().build(); //404
                }

                return  ResponseEntity.ok("Se eliminó correctamente."); //200 OK
    }
}
