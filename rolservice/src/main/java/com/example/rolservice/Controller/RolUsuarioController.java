package com.example.rolservice.Controller;

import com.example.rolservice.Model.RolUsuario;
import com.example.rolservice.Service.RolUsuarioService;
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
@RequestMapping("/api/v1/roles")
@Tag(name = "Rol Usuario", description = "API para la gestion de roles")
public class RolUsuarioController {

    @Autowired
    private RolUsuarioService rolUsuarioService;

    @GetMapping
    @Operation(
            summary = "Listar roles",
            description = "Obtiene la lista de todos los roles registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles encontrados con exito"),
            @ApiResponse(responseCode = "204", description = "No hay roles dentro del sistema")
    })
    public ResponseEntity<List<RolUsuario>> getRol(){
        List<RolUsuario> roles = rolUsuarioService.listarRoles();

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build(); //204 No contenido
        }

        return ResponseEntity.ok(roles); //200 OK
    }

    @PostMapping
    @Operation(
            summary = "Crear rol",
            description = "Registra roles dentro del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Este rol ya se encuentra dentro del sistema"),
            @ApiResponse(responseCode = "201", description = "Rol creado con exito")
    })
    public ResponseEntity<?> createRol(@Valid @RequestBody RolUsuario rolUsuario){
        RolUsuario nuevoRol = rolUsuarioService.guardarRol(rolUsuario);

        if (nuevoRol == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El rol ya existe."); //ERROR 409 ya existe el contenido
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRol); //201 CREADO
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar rol",
            description = "Buscar un rol por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Error, rol no encontrado"),
            @ApiResponse(responseCode = "200", description = "Rol encontrado dentro del sistema")
    })
    public ResponseEntity<RolUsuario> getId(@PathVariable Integer id){
        RolUsuario rolUsuario = rolUsuarioService.buscarPorId(id);

        if (rolUsuario == null){
            return ResponseEntity.notFound().build(); //Error 404 NOT FOUND
        }

        return ResponseEntity.ok(rolUsuario); // 200 OK
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar rol",
            description = "Actualiza el rol por su id asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Error, rol no encontrado"),
            @ApiResponse(responseCode = "200", description = "Rol actualizado con exito")
    })
    public ResponseEntity<RolUsuario> updateRol(@PathVariable Integer id, @Valid @RequestBody RolUsuario rolUsuario){
        RolUsuario rolActualizado = rolUsuarioService.actualizarRol(id, rolUsuario);

        if (rolActualizado == null){
            return ResponseEntity.notFound().build(); // 404 NOT FOUND
        }

        return ResponseEntity.ok(rolActualizado); // 200 OK
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar rol",
            description = "Elimina el rol asociado por su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Error, rol no encontrado"),
            @ApiResponse(responseCode = "200", description = "Rol eliminado con exito")
    })
    public ResponseEntity<String> deleteRol(@PathVariable Integer id){
        boolean eliminado = rolUsuarioService.eliminarRol(id);

                if(!eliminado){
                    return ResponseEntity.notFound().build(); //404
                }

                return  ResponseEntity.ok("Se eliminó correctamente."); //200 OK
    }
}
