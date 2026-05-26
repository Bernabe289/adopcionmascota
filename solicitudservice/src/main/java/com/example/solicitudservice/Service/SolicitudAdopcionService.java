package com.example.solicitudservice.Service;

import com.example.solicitudservice.Client.MascotaClient;
import com.example.solicitudservice.Client.UsuarioClient;
import com.example.solicitudservice.Dto.MascotaDTO;
import com.example.solicitudservice.Dto.UsuarioDTO;
import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Repository.SolicitudAdopcionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudAdopcionService {

    @Autowired
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private MascotaClient mascotaClient;

    public List<SolicitudAdopcion> listarSolicitudes(){
        return solicitudAdopcionRepository.findAll();
    }

    public SolicitudAdopcion buscarPorId(Integer id) {
        return solicitudAdopcionRepository.findById(id).orElse(null);
    }

    public SolicitudAdopcion guardarSolicitud (SolicitudAdopcion solicitudAdopcion){
        if (solicitudAdopcion.getIdUsuario() == null){
            return null;
        }
        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(solicitudAdopcion.getIdUsuario());

            if (usuario == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;

        }
        if (solicitudAdopcion.getIdMascota() == null){
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(solicitudAdopcion.getIdMascota());

            if (mascota == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }

        solicitudAdopcion.setEstadoSolicitud(solicitudAdopcion.getEstadoSolicitud().trim().toUpperCase());

        if (solicitudAdopcion.getObservacionSolicitud() != null) {
            solicitudAdopcion.setObservacionSolicitud(solicitudAdopcion.getObservacionSolicitud().trim());
        }
        return solicitudAdopcionRepository.save(solicitudAdopcion);
    }

    public SolicitudAdopcion actualizarSolicitud(Integer id, SolicitudAdopcion solicitudAdopcion) {
        SolicitudAdopcion solicitudExistente = solicitudAdopcionRepository.findById(id).orElse(null);

        if (solicitudExistente == null) {
            return null;
        }

        if (solicitudAdopcion.getIdUsuario() == null) {
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(solicitudAdopcion.getIdUsuario());

            if (usuario == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }

        if (solicitudAdopcion.getIdMascota() == null) {
            return null;
        }

        try {
            MascotaDTO mascota = mascotaClient.getMascotaById(solicitudAdopcion.getIdMascota());

            if (mascota == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }

        solicitudExistente.setIdUsuario(solicitudAdopcion.getIdUsuario());
        solicitudExistente.setIdMascota(solicitudAdopcion.getIdMascota());
        solicitudExistente.setFechaSolicitud(solicitudAdopcion.getFechaSolicitud());
        solicitudExistente.setEstadoSolicitud(solicitudAdopcion.getEstadoSolicitud().trim().toUpperCase());

        if (solicitudAdopcion.getObservacionSolicitud() != null) {
            solicitudExistente.setObservacionSolicitud(solicitudAdopcion.getObservacionSolicitud().trim());
        }

        return solicitudAdopcionRepository.save(solicitudExistente);
    }

    public boolean eliminarSolicitud(Integer id) {
        if (!solicitudAdopcionRepository.existsById(id)) {
            return false;
        }

        solicitudAdopcionRepository.deleteById(id);
        return true;
    }
}
