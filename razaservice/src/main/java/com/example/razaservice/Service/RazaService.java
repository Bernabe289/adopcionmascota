package com.example.razaservice.Service;

import com.example.razaservice.Client.RazaClient;
import com.example.razaservice.Dto.EspecieDTO;
import com.example.razaservice.Model.Raza;
import com.example.razaservice.Repository.RazaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RazaService {

    @Autowired
    private RazaRepository razaRepository;

    @Autowired
    private RazaClient razaClient;

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
        try {
            EspecieDTO especie = razaClient.getEspecieById(raza.getIdEspecie());

            if (especie == null) {
                return null;
            }
        } catch (FeignException error) {
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
        try {
            EspecieDTO especie = razaClient.getEspecieById(raza.getIdEspecie());

            if (especie == null) {
                return null;
            }
        } catch (FeignException error) {
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