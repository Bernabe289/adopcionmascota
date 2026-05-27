package com.example.seguimientoservice.Service;

import com.example.seguimientoservice.Client.SolicitudClient;
import com.example.seguimientoservice.Dto.SolicitudDTO;
import com.example.seguimientoservice.Model.Seguimiento;
import com.example.seguimientoservice.Repository.SeguimientoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class SeguimientoService {

    private static final Logger logger = LoggerFactory.getLogger(SeguimientoService.class);

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private SolicitudClient solicitudClient;

    public List<Seguimiento> listarSeguimientos() {
        logger.info("Listando seguimientos");
        return seguimientoRepository.findAll();
    }

    public Seguimiento guardarSeguimiento(Seguimiento seguimiento) {

        // Valida que el seguimiento esté asociado a una solicitud
        if (seguimiento.getIdSolicitud() == null) {
            logger.warn("No se pudo crear el seguimiento: idSolicitud viene null");
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(seguimiento.getIdSolicitud());

            if (solicitud == null) {
                logger.warn("No se pudo crear el seguimiento: solicitud ID {} no existe", seguimiento.getIdSolicitud());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear el seguimiento: error al consultar solicitud ID {}", seguimiento.getIdSolicitud());
            return null;
        }

        seguimiento.setObservacionSeguimiento(seguimiento.getObservacionSeguimiento().trim().toUpperCase());

        Seguimiento seguimientoGuardado = seguimientoRepository.save(seguimiento);
        logger.info("Seguimiento creado correctamente con ID {}", seguimientoGuardado.getIdSeguimiento());

        return seguimientoGuardado;
    }

    public Seguimiento buscarPorId(Integer id) {
        logger.info("Buscando seguimiento con ID {}", id);
        return seguimientoRepository.findById(id).orElse(null);
    }

    public Seguimiento actualizarSeguimiento(Integer id, Seguimiento seguimiento) {
        Seguimiento seguimientoExistente = seguimientoRepository.findById(id).orElse(null);

        if (seguimientoExistente == null) {
            logger.warn("No se pudo actualizar el seguimiento: no existe seguimiento con ID {}", id);
            return null;
        }

        // Mantiene solo el ID de solicitud porque está en otro microservicio
        if (seguimiento.getIdSolicitud() == null) {
            logger.warn("No se pudo actualizar el seguimiento ID {}: idSolicitud viene null", id);
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(seguimiento.getIdSolicitud());

            if (solicitud == null) {
                logger.warn("No se pudo actualizar el seguimiento ID {}: solicitud ID {} no existe", id, seguimiento.getIdSolicitud());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar el seguimiento ID {}: error al consultar solicitud ID {}", id, seguimiento.getIdSolicitud());
            return null;
        }

        seguimientoExistente.setFechaSeguimiento(seguimiento.getFechaSeguimiento());
        seguimientoExistente.setObservacionSeguimiento(seguimiento.getObservacionSeguimiento().trim().toUpperCase());
        seguimientoExistente.setIdSolicitud(seguimiento.getIdSolicitud());

        Seguimiento seguimientoActualizado = seguimientoRepository.save(seguimientoExistente);
        logger.info("Seguimiento ID {} actualizado correctamente", seguimientoActualizado.getIdSeguimiento());

        return seguimientoActualizado;
    }

    public boolean eliminarSeguimiento(Integer id) {
        if (!seguimientoRepository.existsById(id)) {
            logger.warn("No se pudo eliminar el seguimiento: no existe seguimiento con ID {}", id);
            return false;
        }

        seguimientoRepository.deleteById(id);
        logger.info("Seguimiento ID {} eliminado correctamente", id);

        return true;
    }
}