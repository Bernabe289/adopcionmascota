package com.example.solicitudservice.Service;

import com.example.solicitudservice.Client.MascotaClient;
import com.example.solicitudservice.Client.UsuarioClient;
import com.example.solicitudservice.Dto.MascotaDTO;
import com.example.solicitudservice.Dto.UsuarioDTO;
import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Repository.SolicitudAdopcionRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SolicitudAdopcionService {

    @Autowired
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private MascotaClient mascotaClient;

    public List<SolicitudAdopcion> listarSolicitudes(){
        log.info("Listando solicitudes de adopción");
        return solicitudAdopcionRepository.findAll();
    }

    public SolicitudAdopcion buscarPorId(Integer id) {
        log.info("Buscando solicitud de adopción con ID {}", id);
        return solicitudAdopcionRepository.findById(id).orElse(null);
    }

    public SolicitudAdopcion guardarSolicitud (SolicitudAdopcion solicitudAdopcion){
        if (solicitudAdopcion.getIdUsuario() == null){
            log.warn("No se pudo crear la solicitud: idUsuario viene null");
            return null;
        }
        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(solicitudAdopcion.getIdUsuario());

            if (usuario == null) {
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear la solicitud: usuario ID {} no existe", solicitudAdopcion.getIdUsuario());
            return null;

        }
        if (solicitudAdopcion.getIdMascota() == null){
            log.warn("No se pudo crear la solicitud: idMascota viene null");
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(solicitudAdopcion.getIdMascota());

            if (mascota == null) {
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear la solicitud: mascota ID {} no existe", solicitudAdopcion.getIdMascota());
            return null;
        }

        solicitudAdopcion.setEstadoSolicitud(solicitudAdopcion.getEstadoSolicitud().trim().toUpperCase());

        if (solicitudAdopcion.getObservacionSolicitud() != null) {
            solicitudAdopcion.setObservacionSolicitud(solicitudAdopcion.getObservacionSolicitud().trim());
        }

        SolicitudAdopcion solicitudGuardada = solicitudAdopcionRepository.save(solicitudAdopcion);

        log.info("Solicitud creada correctamente con ID {}, usuario ID {} y mascota ID {}",
                solicitudGuardada.getIdSolicitud(),
                solicitudGuardada.getIdUsuario(),
                solicitudGuardada.getIdMascota());

        return solicitudGuardada;
    }

    public SolicitudAdopcion actualizarSolicitud(Integer id, SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion solicitudExistente = solicitudAdopcionRepository.findById(id).orElse(null);

        if (solicitudExistente == null) {
            log.warn("No se pudo actualizar la solicitud: no existe solicitud con ID {}", id);
            return null;
        }

        if (solicitudAdopcion.getIdUsuario() == null) {
            log.warn("No se pudo actualizar la solicitud ID {}: idUsuario viene null", id);
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(solicitudAdopcion.getIdUsuario());

            if (usuario == null) {
                log.warn("No se pudo actualizar la solicitud ID {}: usuario ID {} no existe",
                        id, solicitudAdopcion.getIdUsuario());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar la solicitud ID {}: usuario ID {} no existe",
                    id, solicitudAdopcion.getIdUsuario());
            return null;
        }

        if (solicitudAdopcion.getIdMascota() == null) {
            log.warn("No se pudo actualizar la solicitud ID {}: idMascota viene null", id);
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(solicitudAdopcion.getIdMascota());

            if (mascota == null) {
                log.warn("No se pudo actualizar la solicitud ID {}: mascota ID {} no existe",
                        id, solicitudAdopcion.getIdMascota());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar la solicitud ID {}: mascota ID {} no existe",
                    id, solicitudAdopcion.getIdMascota());
            return null;
        }

        solicitudExistente.setIdUsuario(solicitudAdopcion.getIdUsuario());
        solicitudExistente.setIdMascota(solicitudAdopcion.getIdMascota());
        solicitudExistente.setFechaSolicitud(solicitudAdopcion.getFechaSolicitud());
        solicitudExistente.setEstadoSolicitud(solicitudAdopcion.getEstadoSolicitud().trim().toUpperCase());

        if (solicitudAdopcion.getObservacionSolicitud() != null) {
            solicitudExistente.setObservacionSolicitud(solicitudAdopcion.getObservacionSolicitud().trim());
        }

        SolicitudAdopcion solicitudActualizada = solicitudAdopcionRepository.save(solicitudExistente);

        log.info("Solicitud actualizada correctamente con ID {}", solicitudActualizada.getIdSolicitud());

        return solicitudActualizada;
    }

    public boolean eliminarSolicitud(Integer id) {
        if (!solicitudAdopcionRepository.existsById(id)) {
            log.warn("No se pudo eliminar la solicitud: no existe solicitud con ID {}", id);
            return false;
        }

        solicitudAdopcionRepository.deleteById(id);
        log.info("Solicitud eliminada correctamente con ID {}", id);
        return true;
    }
}
