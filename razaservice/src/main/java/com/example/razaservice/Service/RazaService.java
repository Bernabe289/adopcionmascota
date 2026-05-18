package com.example.razaservice.Service;

import com.example.razaservice.Model.Raza;
import com.example.razaservice.Repository.RazaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RazaService {

    @Autowired
    private RazaRepository razaRepository;

    public List<Raza> listarRazas() {
        return razaRepository.findAll();
    }

    public Raza guardarRaza(Raza raza) {
        String nombre = raza.getNombreRaza().trim().toUpperCase();

        if (razaRepository.existsByNombreRazaIgnoreCase(nombre)) {
            return null;
        }

        if (raza.getIdEspecie() == null) {
            return null;
        }

        raza.setNombreRaza(nombre);
        return razaRepository.save(raza);
    }

    public Raza buscarPorId(Integer id) {
        return razaRepository.findById(id).orElse(null);
    }

    public Raza actualizarRaza(Integer id, Raza raza) {
        Raza razaExistente = razaRepository.findById(id).orElse(null);

        if (razaExistente == null) {
            return null;
        }

        String nombre = raza.getNombreRaza().trim().toUpperCase();

        Optional<Raza> razaDuplicada = razaRepository.findByNombreRazaIgnoreCase(nombre);

        if (razaDuplicada.isPresent() && !razaDuplicada.get().getIdRaza().equals(id)) {
            return null;
        }

        if (raza.getIdEspecie() == null) {
            return null;
        }

        razaExistente.setNombreRaza(nombre);
        razaExistente.setIdEspecie(raza.getIdEspecie());

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