package com.example.visitaservice.Service;

import com.example.visitaservice.Client.SolicitudClient;
import com.example.visitaservice.Dto.SolicitudDTO;
import com.example.visitaservice.Model.Visita;
import com.example.visitaservice.Repository.VisitaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class VisitaService {

    private static final Logger logger = LoggerFactory.getLogger(VisitaService.class);

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private SolicitudClient solicitudClient;

    public List<Visita> listarVisitas() {
        logger.info("Listando visitas");
        return visitaRepository.findAll();
    }

    public Visita guardarVisita(Visita visita) {

        // Valida que la visita esté asociada a una solicitud
        if (visita.getIdSolicitud() == null) {
            logger.warn("No se pudo crear la visita: idSolicitud viene null");
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(visita.getIdSolicitud());

            if (solicitud == null) {
                logger.warn("No se pudo crear la visita: solicitud ID {} no existe", visita.getIdSolicitud());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear la visita: error al consultar solicitud ID {}", visita.getIdSolicitud());
            return null;
        }

        visita.setEstadoVisita(visita.getEstadoVisita().trim().toUpperCase());

        Visita visitaGuardada = visitaRepository.save(visita);
        logger.info("Visita creada correctamente con ID {}", visitaGuardada.getIdVisita());

        return visitaGuardada;
    }

    public Visita buscarPorId(Integer id) {
        logger.info("Buscando visita con ID {}", id);
        return visitaRepository.findById(id).orElse(null);
    }

    public Visita actualizarVisita(Integer id, Visita visita) {
        Visita visitaExistente = visitaRepository.findById(id).orElse(null);

        if (visitaExistente == null) {
            logger.warn("No se pudo actualizar la visita: no existe visita con ID {}", id);
            return null;
        }

        // Mantiene solo el ID de solicitud porque está en otro microservicio
        if (visita.getIdSolicitud() == null) {
            logger.warn("No se pudo actualizar la visita ID {}: idSolicitud viene null", id);
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(visita.getIdSolicitud());

            if (solicitud == null) {
                logger.warn("No se pudo actualizar la visita ID {}: solicitud ID {} no existe", id, visita.getIdSolicitud());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar la visita ID {}: error al consultar solicitud ID {}", id, visita.getIdSolicitud());
            return null;
        }

        visitaExistente.setFechaVisita(visita.getFechaVisita());
        visitaExistente.setEstadoVisita(visita.getEstadoVisita().trim().toUpperCase());
        visitaExistente.setIdSolicitud(visita.getIdSolicitud());

        Visita visitaActualizada = visitaRepository.save(visitaExistente);
        logger.info("Visita ID {} actualizada correctamente", visitaActualizada.getIdVisita());

        return visitaActualizada;
    }

    public boolean eliminarVisita(Integer id) {
        if (!visitaRepository.existsById(id)) {
            logger.warn("No se pudo eliminar la visita: no existe visita con ID {}", id);
            return false;
        }

        visitaRepository.deleteById(id);
        logger.info("Visita ID {} eliminada correctamente", id);

        return true;
    }
}