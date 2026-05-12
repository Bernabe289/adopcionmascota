package com.adopcion.adopcionmascota.Service;

import com.adopcion.adopcionmascota.Model.Especie;
import com.adopcion.adopcionmascota.Model.Raza;
import com.adopcion.adopcionmascota.Repository.EspecieRepository;
import com.adopcion.adopcionmascota.Repository.RazaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RazaService {
    @Autowired
    private RazaRepository razaRepository;

    @Autowired
    private EspecieRepository especieRepository;

    public List<Raza> listarRazas() {
        return razaRepository.findAll();
    }

    public Raza guardarRaza(Raza raza) {
        String nombre = raza.getNombreRaza().trim().toUpperCase();

        if (razaRepository.existsByNombreRazaIgnoreCase(nombre)) {
            return null;
        }
        if(raza.getEspecie() == null || raza.getEspecie().getIdEspecie() == null){
            return null;
        }
        Especie especie = especieRepository.findById(raza.getEspecie().getIdEspecie()).orElse(null);

        if (especie == null) {
            return null;
        }
        raza.setNombreRaza(nombre);
        raza.setEspecie(especie);
        return razaRepository.save(raza);
    }
    public Raza buscarPorId(Integer id){
        return razaRepository.findById(id).orElse(null);
    }

    public Raza actualizarRaza(Integer id, Raza raza) {
        Raza razaExistente = razaRepository.findById(id).orElse(null);

        if (razaExistente == null) {
            return null;
        }
        String nombre = raza.getNombreRaza().trim().toUpperCase();
        Optional<Raza> razaConMismoNombre = razaRepository.findByNombreRazaIgnoreCase(nombre);

        if(razaConMismoNombre.isPresent() && !razaConMismoNombre.get().getIdraza().equals(id)){
            return null;
        }

        if(raza.getEspecie() == null || raza.getEspecie().getIdEspecie() == null){
            return null;
        }

        Especie especie = especieRepository.findById(raza.getEspecie().getIdEspecie()).orElse(null);
        if(especie == null){
            return null;
        }
        razaExistente.setNombreRaza(nombre);
        razaExistente.setEspecie(especie);

        return razaRepository.save(razaExistente);
    }

    public boolean eliminarRaza(Integer id) {
        if (!razaRepository.existsById(id)) {
            return false;
        }

        razaRepository.deleteById(id);
        return true;
    }
}

