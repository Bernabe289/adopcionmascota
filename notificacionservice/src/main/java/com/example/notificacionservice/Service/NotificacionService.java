package com.example.notificacionservice.Service;

import com.example.notificacionservice.Model.Notificacion;
import com.example.notificacionservice.Repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Notificacion guardarNotificacion(Notificacion notificacion) {

        // Valida que la notificación esté asociada a un usuario
        if (notificacion.getIdUsuario() == null) {
            return null;
        }

        notificacion.setMensajeNotificacion(notificacion.getMensajeNotificacion().trim().toUpperCase());
        notificacion.setEstadoNotificacion(notificacion.getEstadoNotificacion().trim().toUpperCase());

        return notificacionRepository.save(notificacion);
    }

    public Notificacion buscarPorId(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public Notificacion actualizarNotificacion(Integer id, Notificacion notificacion) {
        Notificacion notificacionExistente = notificacionRepository.findById(id).orElse(null);

        if (notificacionExistente == null) {
            return null;
        }

        // Mantiene solo el ID de usuario porque Usuario está en otro microservicio
        if (notificacion.getIdUsuario() == null) {
            return null;
        }

        notificacionExistente.setMensajeNotificacion(notificacion.getMensajeNotificacion().trim().toUpperCase());
        notificacionExistente.setFechaNotificacion(notificacion.getFechaNotificacion());
        notificacionExistente.setEstadoNotificacion(notificacion.getEstadoNotificacion().trim().toUpperCase());
        notificacionExistente.setIdUsuario(notificacion.getIdUsuario());

        return notificacionRepository.save(notificacionExistente);
    }

    public boolean eliminarNotificacion(Integer id) {
        if (!notificacionRepository.existsById(id)) {
            return false;
        }

        notificacionRepository.deleteById(id);
        return true;
    }
}
