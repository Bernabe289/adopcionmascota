package com.example.notificacionservice.Service;

import com.example.notificacionservice.Client.UsuarioClient;
import com.example.notificacionservice.Dto.UsuarioDTO;
import com.example.notificacionservice.Model.Notificacion;
import com.example.notificacionservice.Repository.NotificacionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class NotificacionService {

    private static final Logger logger = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Notificacion> listarNotificaciones() {
        logger.info("Listando notificaciones");
        return notificacionRepository.findAll();
    }

    public Notificacion guardarNotificacion(Notificacion notificacion) {

        // Valida que la notificación esté asociada a un usuario
        if (notificacion.getIdUsuario() == null) {
            logger.warn("No se pudo crear la notificación: idUsuario viene null");
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(notificacion.getIdUsuario());

            if (usuario == null) {
                logger.warn("No se pudo crear la notificación: usuario ID {} no existe", notificacion.getIdUsuario());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo crear la notificación: error al consultar usuario ID {}", notificacion.getIdUsuario());
            return null;
        }

        notificacion.setMensajeNotificacion(notificacion.getMensajeNotificacion().trim().toUpperCase());
        notificacion.setEstadoNotificacion(notificacion.getEstadoNotificacion().trim().toUpperCase());

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        logger.info("Notificación creada correctamente con ID {}", notificacionGuardada.getIdNotificacion());

        return notificacionGuardada;
    }

    public Notificacion buscarPorId(Integer id) {
        logger.info("Buscando notificación con ID {}", id);
        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion actualizarNotificacion(Integer id, Notificacion notificacion) {
        Notificacion notificacionExistente = notificacionRepository.findById(id).orElse(null);

        if (notificacionExistente == null) {
            logger.warn("No se pudo actualizar la notificación: no existe notificación con ID {}", id);
            return null;
        }

        // Mantiene solo el ID de usuario porque Usuario está en otro microservicio
        if (notificacion.getIdUsuario() == null) {
            logger.warn("No se pudo actualizar la notificación ID {}: idUsuario viene null", id);
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(notificacion.getIdUsuario());

            if (usuario == null) {
                logger.warn("No se pudo actualizar la notificación ID {}: usuario ID {} no existe", id, notificacion.getIdUsuario());
                return null;
            }
        } catch (FeignException error) {
            logger.warn("No se pudo actualizar la notificación ID {}: error al consultar usuario ID {}", id, notificacion.getIdUsuario());
            return null;
        }

        notificacionExistente.setMensajeNotificacion(notificacion.getMensajeNotificacion().trim().toUpperCase());
        notificacionExistente.setFechaNotificacion(notificacion.getFechaNotificacion());
        notificacionExistente.setEstadoNotificacion(notificacion.getEstadoNotificacion().trim().toUpperCase());
        notificacionExistente.setIdUsuario(notificacion.getIdUsuario());

        Notificacion notificacionActualizada = notificacionRepository.save(notificacionExistente);
        logger.info("Notificación ID {} actualizada correctamente", notificacionActualizada.getIdNotificacion());

        return notificacionActualizada;
    }

    public boolean eliminarNotificacion(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            logger.warn("No se pudo eliminar la notificación: no existe notificación con ID {}", id);
            return false;
        }

        notificacionRepository.deleteById(id);
        logger.info("Notificación ID {} eliminada correctamente", id);

        return true;
    }
}
