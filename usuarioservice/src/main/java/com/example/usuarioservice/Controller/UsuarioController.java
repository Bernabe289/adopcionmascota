package com.example.usuarioservice.Controller;

import com.example.usuarioservice.Model.Usuario;
import com.example.usuarioservice.Service.UsuarioService;
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
@RequestMapping("/api/v1/usuarios")
@Tag(name="Usuarios", description = "API para la gestión de usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene la lista de todos los usuarios registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados con exito"),
            @ApiResponse(responseCode = "204", description = "No existen usuarios registrados")
    })
    public ResponseEntity<List<Usuario>> getUsuario(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.ok(usuarios); //200
    }

    @PostMapping
    @Operation(
            summary = "Crear usuario",
            description = "Registra usuario en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado con exito."),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe o hay conflicto con los datos ingresados")
    })
    public ResponseEntity<?> createUsuario(@Valid @RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);

        if (nuevoUsuario == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo crear el usuario. Verifique que los datos sean correctos.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuario" ,
            description = "Busca un usuario por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado con exito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado en el sistema")
    })
    public ResponseEntity<Usuario> getUsuarioById(@Parameter(description = "ID del usuario", example = "1")
                                                      @PathVariable Integer id){
        Usuario usuario = usuarioService.buscarPorId(id);

        if(usuario == null){
            return ResponseEntity.notFound().build(); //404
        }
        return ResponseEntity.ok(usuario); //200
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza usuario por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado con exito"),
            @ApiResponse(responseCode = "409", description = "El usuario no se pudo actualizar, hay conflicto con los datos ingresados")
    })
    public ResponseEntity<?> updateUsuario(@Parameter(description = "ID del usuario a actualizar", example = "1")
                                               @PathVariable Integer id, @Valid @RequestBody Usuario usuario){
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);

        if(usuarioActualizado == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se pudo actualizar el usuario. Verifique que los datos sean correctos.");
        }
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Error, el usuario no ha sido encontrado dentro del sistema."),
            @ApiResponse(responseCode = "200", description = "El usuario ha sido eliminado con exito.")
    })
    public ResponseEntity<String> deleteUsuario(@Parameter(description = "Id del usuario a eliminar", example = "1")
                                                    @PathVariable Integer id){
        boolean userEliminado = usuarioService.eliminarUsuario(id);

        if (!userEliminado){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado."); //404
        }
        return ResponseEntity.ok("Usuario eliminado."); //200
    }
}
