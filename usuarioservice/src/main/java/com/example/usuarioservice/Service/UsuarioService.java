package com.example.usuarioservice.Service;

import com.example.usuarioservice.Client.UsuarioClient;
import com.example.usuarioservice.Dto.RolUsuarioDTO;
import com.example.usuarioservice.Model.Usuario;
import com.example.usuarioservice.Repository.UsuarioRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios(){
        log.info("Listando usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario){
        String rutNormalizado = usuario.getRutUsuario().trim().toUpperCase();
        String emailNormalizado = usuario.getEmailUsuario().trim().toLowerCase();

        if (usuarioRepository.existsByRutUsuarioIgnoreCase(rutNormalizado)){
            log.warn("No se pudo crear el usuario: RUT {} ya existe", rutNormalizado);
            return null;
        }

        if (usuarioRepository.existsByEmailUsuarioIgnoreCase(emailNormalizado)){
            log.warn("No se pudo crear el usuario: email {} ya existe", emailNormalizado);
            return null;
        }

        if (usuario.getIdRol() == null) {
            log.warn("No se pudo crear el usuario: idRol viene null");
            return null;
        }

        try {
            RolUsuarioDTO rol = usuarioClient.getRolById(usuario.getIdRol());

            if (rol == null) {
                log.warn("No se pudo crear el usuario: rol ID {} no existe", usuario.getIdRol());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear el usuario: rol ID {} no existe", usuario.getIdRol());
            return null;
        }

        usuario.setRutUsuario(rutNormalizado);
        usuario.setEmailUsuario(emailNormalizado);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        log.info("Usuario creado correctamente con ID {} y rol ID {}",
                usuarioGuardado.getIdUsuario(),
                usuarioGuardado.getIdRol());

        return usuarioGuardado;
    }

    public Usuario buscarPorId(Integer id){
        log.info("Buscando usuario con ID {}", id);
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);

        if(usuarioExistente == null){
            log.warn("No se pudo actualizar el usuario: no existe usuario con ID {}", id);
            return null;
        }

        String rutNormalizado = usuario.getRutUsuario().trim().toUpperCase();
        String emailNormalizado = usuario.getEmailUsuario().trim().toLowerCase();

        Optional<Usuario> usuarioRut = usuarioRepository.findByRutUsuarioIgnoreCase(rutNormalizado);

        if(usuarioRut.isPresent() && !usuarioRut.get().getIdUsuario().equals(id)){
            log.warn("No se pudo actualizar el usuario ID {}: RUT {} ya pertenece a otro usuario",
                    id, rutNormalizado);
            return null;
        }

        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmailUsuarioIgnoreCase(emailNormalizado);

        if(usuarioEmail.isPresent() && !usuarioEmail.get().getIdUsuario().equals(id)){
            log.warn("No se pudo actualizar el usuario ID {}: email {} ya pertenece a otro usuario",
                    id, emailNormalizado);
            return null;
        }

        if(usuario.getIdRol() == null){
            log.warn("No se pudo actualizar el usuario ID {}: idRol viene null", id);
            return null;
        }

        try {
            RolUsuarioDTO rol = usuarioClient.getRolById(usuario.getIdRol());

            if (rol == null) {
                log.warn("No se pudo actualizar el usuario ID {}: rol ID {} no existe",
                        id, usuario.getIdRol());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar el usuario ID {}: rol ID {} no existe",
                    id, usuario.getIdRol());
            return null;
        }

        usuarioExistente.setRutUsuario(rutNormalizado);
        usuarioExistente.setEmailUsuario(emailNormalizado);
        usuarioExistente.setContrasenaUsuario(usuario.getContrasenaUsuario());
        usuarioExistente.setPnombreUsuario(usuario.getPnombreUsuario());
        usuarioExistente.setSnombreUsuario(usuario.getSnombreUsuario());
        usuarioExistente.setAppaternoUsuario(usuario.getAppaternoUsuario());
        usuarioExistente.setApmaternoUsuario(usuario.getApmaternoUsuario());
        usuarioExistente.setTelefonoUsuario(usuario.getTelefonoUsuario());
        usuarioExistente.setDireccionUsuario(usuario.getDireccionUsuario());
        usuarioExistente.setEstadoUsuario(usuario.getEstadoUsuario());
        usuarioExistente.setIdRol(usuario.getIdRol());

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);

        log.info("Usuario actualizado correctamente con ID {}", usuarioActualizado.getIdUsuario());

        return usuarioActualizado;
    }

    public boolean eliminarUsuario(Integer id){
        if(!usuarioRepository.existsById(id)){
            log.warn("No se pudo eliminar el usuario: no existe usuario con ID {}", id);
            return false;
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado correctamente con ID {}", id);
        return true;
    }
}