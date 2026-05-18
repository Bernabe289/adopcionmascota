package com.example.rolservice.Service;

import com.example.rolservice.Model.RolUsuario;
import com.example.rolservice.Repository.RolUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolUsuarioService {

    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;

    public List<RolUsuario> listarRoles(){
        return rolUsuarioRepository.findAll();
    }

    public RolUsuario guardarRol(RolUsuario rolUsuario){
        String nombre = rolUsuario.getNombreRol().trim().toUpperCase();

        if (rolUsuarioRepository.existsByNombreRolIgnoreCase(nombre)){
            return null;
        }
        rolUsuario.setNombreRol(nombre);
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

        String nombreNormalizado = rolUsuario.getNombreRol().trim().toUpperCase();

        Optional<RolUsuario> rolDuplicado = rolUsuarioRepository.findByNombreRolIgnoreCase(nombreNormalizado);
        if(rolDuplicado.isPresent() && !rolDuplicado.get().getIdRol().equals(id)){
            return null;
        }

        rolExistente.setNombreRol(nombreNormalizado);
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