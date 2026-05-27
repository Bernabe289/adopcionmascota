package com.example.rolservice.Service;

import com.example.rolservice.Model.RolUsuario;
import com.example.rolservice.Repository.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class RolUsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(RolUsuarioService.class);

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> listarRoles(){
        logger.info("Listando roles");
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario guardarRol(RolUsuario rolUsuario){
        String nombre = rolUsuario.getNombreRol().trim().toUpperCase();

        if (rolUsuarioRepository.existsByNombreRolIgnoreCase(nombre)){
            logger.warn("No se pudo crear el rol: ya existe un rol con el nombre {}", nombre);
            return null;
        }

        rolUsuario.setNombreRol(nombre);

        RolUsuario rolGuardado = rolUsuarioRepository.save(rolUsuario);
        logger.info("Rol creado correctamente con ID {}", rolGuardado.getIdRol());

        return rolGuardado;
    }

    public RolUsuario buscarPorId(Integer id){
        logger.info("Buscando rol con ID {}", id);
        return rolUsuarioRepository.findById(id).orElse(null);
    }

    public RolUsuario actualizarRol(Integer id, RolUsuario rolUsuario){
        RolUsuario rolExistente = rolUsuarioRepository.findById(id).orElse(null);

        if(rolExistente == null){
            logger.warn("No se pudo actualizar el rol: no existe rol con ID {}", id);
            return null;
        }

        String nombreNormalizado = rolUsuario.getNombreRol().trim().toUpperCase();

        Optional<RolUsuario> rolDuplicado = rolUsuarioRepository.findByNombreRolIgnoreCase(nombreNormalizado);

        if(rolDuplicado.isPresent() && !rolDuplicado.get().getIdRol().equals(id)){
            logger.warn("No se pudo actualizar el rol ID {}: ya existe otro rol con el nombre {}", id, nombreNormalizado);
            return null;
        }

        rolExistente.setNombreRol(nombreNormalizado);

        RolUsuario rolActualizado = rolUsuarioRepository.save(rolExistente);
        logger.info("Rol ID {} actualizado correctamente", rolActualizado.getIdRol());

        return rolActualizado;
    }

    public boolean eliminarRol(Integer id){
        if(!rolUsuarioRepository.existsById(id)){
            logger.warn("No se pudo eliminar el rol: no existe rol con ID {}", id);
            return false;
        }

        rolUsuarioRepository.deleteById(id);
        logger.info("Rol ID {} eliminado correctamente", id);

        return true;
    }
}