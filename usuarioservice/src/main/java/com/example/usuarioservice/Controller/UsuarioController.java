package com.example.usuarioservice.Controller;

import com.example.usuarioservice.Model.Usuario;
import com.example.usuarioservice.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.ok(usuarios); //200
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);

        if (nuevoUsuario == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo crear el usuario. Verifique que los datos sean correctos.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id){
        Usuario usuario = usuarioService.buscarPorId(id);

        if(usuario == null){
            return ResponseEntity.notFound().build(); //404
        }
        return ResponseEntity.ok(usuario); //200
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer id, @Valid @RequestBody Usuario usuario){
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);

        if(usuarioActualizado == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo actualizar el usuario. Verifique que los datos sean correctos.");
        }
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Integer id){
        boolean userEliminado = usuarioService.eliminarUsuario(id);

        if (!userEliminado){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado."); //404
        }
        return ResponseEntity.ok("Usuario eliminado."); //200
    }
}
