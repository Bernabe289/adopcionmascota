package com.example.historialvetservice.Service;

import com.example.historialvetservice.Client.MascotaClient;
import com.example.historialvetservice.Dto.MascotaDTO;
import com.example.historialvetservice.Model.HistorialVet;
import com.example.historialvetservice.Repository.HistorialVetRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class HistorialVetService {

    private static final Logger logger = LoggerFactory.getLogger(HistorialVetService.class);

    @Autowired
    private HistorialVetRepository historialVetRepository;

    @Autowired
    private MascotaClient mascotaClient;

    public List<HistorialVet> listarHistoriales() {
        logger.info("Listando historiales veterinarios");
        return historialVetRepository.findAll();
    }

    public HistorialVet guardarHistorial(HistorialVet historialVet) {

        // Valida que el historial esté asociado a una mascota
        if (historialVet.getIdMascota() == null) {
            logger.warn("No se pudo crear el historial veterinario: idMascota viene null");
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(historialVet.getIdMascota());

            if (mascota == null) {
                logger.warn("No se pudo crear el historial veterinario: mascota ID {} no existe", historialVet.getIdMascota());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear el historial veterinario: error al consultar mascota ID {}", historialVet.getIdMascota());
            return null;
        }

        historialVet.setDescripcionHistorial(historialVet.getDescripcionHistorial().trim().toUpperCase());

        HistorialVet historialGuardado = historialVetRepository.save(historialVet);
        logger.info("Historial veterinario creado correctamente con ID {}", historialGuardado.getIdHistorial());

        return historialGuardado;
    }

    public HistorialVet buscarPorId(Integer id) {
        logger.info("Buscando historial veterinario con ID {}", id);
        return historialVetRepository.findById(id).orElse(null);
    }

    public HistorialVet actualizarHistorial(Integer id, HistorialVet historialVet) {
        HistorialVet historialExistente = historialVetRepository.findById(id).orElse(null);

        if (historialExistente == null) {
            logger.warn("No se pudo actualizar el historial veterinario: no existe historial con ID {}", id);
            return null;
        }

        // Mantiene solo el ID de mascota porque Mascota está en otro microservicio
        if (historialVet.getIdMascota() == null) {
            logger.warn("No se pudo actualizar el historial veterinario ID {}: idMascota viene null", id);
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(historialVet.getIdMascota());

            if (mascota == null) {
                logger.warn("No se pudo actualizar el historial veterinario ID {}: mascota ID {} no existe", id, historialVet.getIdMascota());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar el historial veterinario ID {}: error al consultar mascota ID {}", id, historialVet.getIdMascota());
            return null;
        }

        historialExistente.setDescripcionHistorial(historialVet.getDescripcionHistorial().trim().toUpperCase());
        historialExistente.setFechaRegistroHistorial(historialVet.getFechaRegistroHistorial());
        historialExistente.setIdMascota(historialVet.getIdMascota());

        HistorialVet historialActualizado = historialVetRepository.save(historialExistente);
        logger.info("Historial veterinario ID {} actualizado correctamente", historialActualizado.getIdHistorial());

        return historialActualizado;
    }

    public boolean eliminarHistorial(Integer id) {
        if (!historialVetRepository.existsById(id)) {
            logger.warn("No se pudo eliminar el historial veterinario: no existe historial con ID {}", id);
            return false;
        }

        historialVetRepository.deleteById(id);
        logger.info("Historial veterinario ID {} eliminado correctamente", id);

        return true;
    }
}