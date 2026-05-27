package com.example.mascotaservice.Service;

import com.example.mascotaservice.Client.RazaClient;
import com.example.mascotaservice.Client.RefugioClient;
import com.example.mascotaservice.Dto.RazaDTO;
import com.example.mascotaservice.Dto.RefugioDTO;
import com.example.mascotaservice.Model.Mascota;
import com.example.mascotaservice.Repository.MascotaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MascotaService {

    private static final Logger logger = LoggerFactory.getLogger(MascotaService.class);

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private RazaClient razaClient;

    @Autowired
    private RefugioClient refugioClient;

    public List<Mascota> listarMascotas() {
        logger.info("Listando mascotas");
        return mascotaRepository.findAll();
    }

    public Mascota guardarMascota(Mascota mascota) {

        // Valida que la mascota tenga raza y refugio asignados antes de guardar
        if (mascota.getIdRaza() == null || mascota.getIdRefugio() == null) {
            logger.warn("No se pudo crear la mascota: idRaza o idRefugio viene null");
            return null;
        }

        try {
            RazaDTO raza = razaClient.getRazaById(mascota.getIdRaza());

            if (raza == null) {
                logger.warn("No se pudo crear la mascota: raza ID {} no existe", mascota.getIdRaza());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear la mascota: error al consultar raza ID {}", mascota.getIdRaza());
            return null;
        }

        try {
            RefugioDTO refugio = refugioClient.getRefugioById(mascota.getIdRefugio());

            if (refugio == null) {
                logger.warn("No se pudo crear la mascota: refugio ID {} no existe", mascota.getIdRefugio());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear la mascota: error al consultar refugio ID {}", mascota.getIdRefugio());
            return null;
        }

        mascota.setNombreMascota(mascota.getNombreMascota().trim().toUpperCase());
        mascota.setSexoMascota(mascota.getSexoMascota().trim().toUpperCase());
        mascota.setTamanoMascota(mascota.getTamanoMascota().trim().toUpperCase());
        mascota.setEstadoMascota(mascota.getEstadoMascota().trim().toUpperCase());

        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        logger.info("Mascota creada correctamente con ID {}", mascotaGuardada.getIdMascota());

        return mascotaGuardada;
    }

    public Mascota buscarPorId(Integer id) {
        logger.info("Buscando mascota con ID {}", id);
        return mascotaRepository.findById(id).orElse(null);
    }

    public Mascota actualizarMascota(Integer id, Mascota mascota) {
        Mascota mascotaExistente = mascotaRepository.findById(id).orElse(null);

        if (mascotaExistente == null) {
            logger.warn("No se pudo actualizar la mascota: no existe mascota con ID {}", id);
            return null;
        }

        // Actualiza la mascota manteniendo solo los IDs de raza y refugio
        if (mascota.getIdRaza() == null || mascota.getIdRefugio() == null) {
            logger.warn("No se pudo actualizar la mascota ID {}: idRaza o idRefugio viene null", id);
            return null;
        }

        try {
            RazaDTO raza = razaClient.getRazaById(mascota.getIdRaza());

            if (raza == null) {
                logger.warn("No se pudo actualizar la mascota ID {}: raza ID {} no existe", id, mascota.getIdRaza());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar la mascota ID {}: error al consultar raza ID {}", id, mascota.getIdRaza());
            return null;
        }

        try {
            RefugioDTO refugio = refugioClient.getRefugioById(mascota.getIdRefugio());

            if (refugio == null) {
                logger.warn("No se pudo actualizar la mascota ID {}: refugio ID {} no existe", id, mascota.getIdRefugio());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar la mascota ID {}: error al consultar refugio ID {}", id, mascota.getIdRefugio());
            return null;
        }

        mascotaExistente.setNombreMascota(mascota.getNombreMascota().trim().toUpperCase());
        mascotaExistente.setEdadMascota(mascota.getEdadMascota());
        mascotaExistente.setSexoMascota(mascota.getSexoMascota().trim().toUpperCase());
        mascotaExistente.setTamanoMascota(mascota.getTamanoMascota().trim().toUpperCase());
        mascotaExistente.setEstadoMascota(mascota.getEstadoMascota().trim().toUpperCase());
        mascotaExistente.setDescripcionMascota(mascota.getDescripcionMascota());
        mascotaExistente.setIdRaza(mascota.getIdRaza());
        mascotaExistente.setIdRefugio(mascota.getIdRefugio());

        Mascota mascotaActualizada = mascotaRepository.save(mascotaExistente);
        logger.info("Mascota ID {} actualizada correctamente", mascotaActualizada.getIdMascota());

        return mascotaActualizada;
    }

    public boolean eliminarMascota(Integer id) {
        if (!mascotaRepository.existsById(id)) {
            logger.warn("No se pudo eliminar la mascota: no existe mascota con ID {}", id);
            return false;
        }

        mascotaRepository.deleteById(id);
        logger.info("Mascota ID {} eliminada correctamente", id);

        return true;
    }
}