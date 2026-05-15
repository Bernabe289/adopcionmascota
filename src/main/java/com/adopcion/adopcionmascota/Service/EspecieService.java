package com.adopcion.adopcionmascota.Service;

import com.adopcion.adopcionmascota.Model.Especie;
import com.adopcion.adopcionmascota.Repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> listarEspecies(){
        return especieRepository.findAll();
    }

    // Valida que no exista una especie duplicada antes de guardar
    public Especie guardarEspecie(Especie especie){
        String nombre = especie.getNombreEspecie().trim().toUpperCase();

        if(especieRepository.existsByNombreEspecieIgnoreCase(nombre)){
            return null;
        }

        especie.setNombreEspecie(nombre);
        return especieRepository.save(especie);
    }

    public Especie buscarPorId(Integer id){
        return especieRepository.findById(id).orElse(null);
    }

    public Especie actualizarEspecie(Integer id, Especie especie){
        Especie especieExistente = especieRepository.findById(id).orElse(null);

        if(especieExistente == null){
            return null;
        }

        String nombre = especie.getNombreEspecie().trim().toUpperCase();

        Optional<Especie> especieConMismoNombre = especieRepository.findByNombreEspecieIgnoreCase(nombre);

        if(especieConMismoNombre.isPresent() && !especieConMismoNombre.get().getIdEspecie().equals(id)){
            return null;
        }

        especieExistente.setNombreEspecie(nombre);
        return especieRepository.save(especieExistente);
    }

    public boolean eliminarEspecie(Integer id){
        if(!especieRepository.existsById(id)){
            return false;
        }

        especieRepository.deleteById(id);
        return true;
    }
}