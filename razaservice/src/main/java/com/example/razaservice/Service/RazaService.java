package com.example.razaservice.Service;

import com.example.razaservice.Client.RazaClient;
import com.example.razaservice.Dto.EspecieDTO;
import com.example.razaservice.Model.Raza;
import com.example.razaservice.Repository.RazaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class RazaService {

    private static final Logger logger = LoggerFactory.getLogger(RazaService.class);

    @Autowired
    private RazaRepository razaRepository;

    @Autowired
    private RazaClient razaClient;

    public List<Raza> listarRazas() {
        logger.info("Listando razas");
        return razaRepository.findAll();
    }

    public Raza guardarRaza(Raza raza) {
        String nombre = raza.getNombreRaza().trim().toUpperCase();

        if (razaRepository.existsByNombreRazaIgnoreCase(nombre)) {
            logger.warn("No se pudo crear la raza: ya existe una raza con el nombre {}", nombre);
            return null;
        }

        if (raza.getIdEspecie() == null) {
            logger.warn("No se pudo crear la raza: idEspecie viene null");
            return null;
        }

        try {
            EspecieDTO especie = razaClient.getEspecieById(raza.getIdEspecie());

            if (especie == null) {
                logger.warn("No se pudo crear la raza: especie ID {} no existe", raza.getIdEspecie());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear la raza: error al consultar especie ID {}", raza.getIdEspecie());
            return null;
        }

        raza.setNombreRaza(nombre);

        Raza razaGuardada = razaRepository.save(raza);
        logger.info("Raza creada correctamente con ID {}", razaGuardada.getIdRaza());

        return razaGuardada;
    }

    public Raza buscarPorId(Integer id) {
        logger.info("Buscando raza con ID {}", id);
        return razaRepository.findById(id).orElse(null);
    }

    public Raza actualizarRaza(Integer id, Raza raza) {
        Raza razaExistente = razaRepository.findById(id).orElse(null);

        if (razaExistente == null) {
            logger.warn("No se pudo actualizar la raza: no existe raza con ID {}", id);
            return null;
        }

        String nombre = raza.getNombreRaza().trim().toUpperCase();

        Optional<Raza> razaDuplicada = razaRepository.findByNombreRazaIgnoreCase(nombre);

        if (razaDuplicada.isPresent() && !razaDuplicada.get().getIdRaza().equals(id)) {
            logger.warn("No se pudo actualizar la raza ID {}: ya existe otra raza con el nombre {}", id, nombre);
            return null;
        }

        if (raza.getIdEspecie() == null) {
            logger.warn("No se pudo actualizar la raza ID {}: idEspecie viene null", id);
            return null;
        }

        try {
            EspecieDTO especie = razaClient.getEspecieById(raza.getIdEspecie());

            if (especie == null) {
                logger.warn("No se pudo actualizar la raza ID {}: especie ID {} no existe", id, raza.getIdEspecie());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar la raza ID {}: error al consultar especie ID {}", id, raza.getIdEspecie());
            return null;
        }

        razaExistente.setNombreRaza(nombre);
        razaExistente.setIdEspecie(raza.getIdEspecie());

        Raza razaActualizada = razaRepository.save(razaExistente);
        logger.info("Raza ID {} actualizada correctamente", razaActualizada.getIdRaza());

        return razaActualizada;
    }

    public boolean eliminarRaza(Integer id) {
        if (!razaRepository.existsById(id)) {
            logger.warn("No se pudo eliminar la raza: no existe raza con ID {}", id);
            return false;
        }

        razaRepository.deleteById(id);
        logger.info("Raza ID {} eliminada correctamente", id);

        return true;
    }
}