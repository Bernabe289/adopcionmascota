package com.adopcion.adopcionmascota.Service;

import com.adopcion.adopcionmascota.Model.RolUsuario;
import com.adopcion.adopcionmascota.Repository.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolUsuarioService {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> listarRoles(){
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario guardarRol(RolUsuario rolUsuario){
        if (rolUsuarioRepository.existsByNombreRol(rolUsuario.getNombreRol())){
            return null;
        }
        return rolUsuarioRepository.save(rolUsuario);
    }

    public RolUsuario buscarPorId(Integer id){
        return rolUsuarioRepository.findById(id).orElse(null);
    }

    public RolUsuario actualizarRol(Integer id, RolUsuario rolUsuario){
        RolUsuario rolExistente = rolUsuarioRepository.findById(id).orElse(null);

        if(rolExistente == null){
            return null;
        }

        rolExistente.setNombreRol(rolUsuario.getNombreRol());
        return rolUsuarioRepository.save(rolExistente);
    }

    public boolean eliminarRol(Integer id){
        if(!rolUsuarioRepository.existsById(id)){
            return false;
        }
        rolUsuarioRepository.deleteById(id);
        return true;
    }

}
