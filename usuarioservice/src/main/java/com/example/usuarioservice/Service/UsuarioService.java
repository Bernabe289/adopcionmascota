package com.example.usuarioservice.Service;
import com.example.usuarioservice.Dto.RolUsuarioDTO;
import feign.FeignException;
import com.example.usuarioservice.Client.UsuarioClient;
import com.example.usuarioservice.Model.Usuario;
import com.example.usuarioservice.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioClient usuarioClient;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario){
        String rutNormalizado = usuario.getRutUsuario().trim().toUpperCase();
        String emailNormalizado = usuario.getEmailUsuario().trim().toLowerCase();

        if (usuarioRepository.existsByRutUsuarioIgnoreCase(rutNormalizado)){
            return null;
        }

        if (usuarioRepository.existsByEmailUsuarioIgnoreCase(emailNormalizado)){
            return null;
        }

        if (usuario.getIdRol() == null) {
            return null;
        }
        try {
            RolUsuarioDTO rol = usuarioClient.getRolById(usuario.getIdRol());

            if (rol == null) {
                return null;
            }
        } catch (FeignException error) {
            return null;
        }

        usuario.setRutUsuario(rutNormalizado);
        usuario.setEmailUsuario(emailNormalizado);

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId (Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario actualizarUsuario(Integer id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);

        if(usuarioExistente == null){
            return null;
        }

        String rutNormalizado = usuario.getRutUsuario().trim().toUpperCase();
        String emailNormalizado = usuario.getEmailUsuario().trim().toLowerCase();

        Optional<Usuario> usuarioRut = usuarioRepository.findByRutUsuarioIgnoreCase(rutNormalizado);

        if(usuarioRut.isPresent() && !usuarioRut.get().getIdUsuario().equals(id)){
            return null;
        }

        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmailUsuarioIgnoreCase(emailNormalizado);

        if(usuarioEmail.isPresent() && !usuarioEmail.get().getIdUsuario().equals(id)){
            return null;
        }

        if(usuario.getIdRol() == null){
            return null;
        }
        try {
            RolUsuarioDTO rol = usuarioClient.getRolById(usuario.getIdRol());

            if (rol == null) {
                return null;
            }
        } catch (FeignException error) {
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

        return usuarioRepository.save(usuarioExistente);
    }

    public boolean eliminarUsuario(Integer id){
        if(!usuarioRepository.existsById(id)){
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }
}