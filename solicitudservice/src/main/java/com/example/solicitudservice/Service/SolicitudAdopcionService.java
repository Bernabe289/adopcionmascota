package com.example.solicitudservice.Service;

import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Repository.SolicitudAdopcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudAdopcionService {
    @Autowired
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    public List<SolicitudAdopcion> listarSolicitudes(){
        return solicitudAdopcionRepository.findAll();
    }

    public SolicitudAdopcion buscarPorId(Integer id) {
        return solicitudAdopcionRepository.findById(id).orElse(null);
    }

    public SolicitudAdopcion guardarSolicitud (SolicitudAdopcion solicitudAdopcion){
        if (solicitudAdopcion.getIdMascota() == null){
            return null;
        }
        if (solicitudAdopcion.getIdSolicitud() == null){
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

        if (solicitudAdopcion.getIdMascota() == null) {
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
