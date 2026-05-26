package com.example.seguimientoservice.Service;

import com.example.seguimientoservice.Client.SolicitudClient;
import com.example.seguimientoservice.Dto.SolicitudDTO;
import com.example.seguimientoservice.Model.Seguimiento;
import com.example.seguimientoservice.Repository.SeguimientoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private SolicitudClient solicitudClient;

    public List<Seguimiento> listarSeguimientos() {
        return seguimientoRepository.findAll();
    }

    public Seguimiento guardarSeguimiento(Seguimiento seguimiento) {

        // Valida que el seguimiento esté asociado a una solicitud
        if (seguimiento.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(seguimiento.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }
        } catch (FeignException error) {
            return null;
        }

        seguimiento.setObservacionSeguimiento(seguimiento.getObservacionSeguimiento().trim().toUpperCase());

        return seguimientoRepository.save(seguimiento);
    }

    public Seguimiento buscarPorId(Integer id) {
        return seguimientoRepository.findById(id).orElse(null);
    }

    public Seguimiento actualizarSeguimiento(Integer id, Seguimiento seguimiento) {
        Seguimiento seguimientoExistente = seguimientoRepository.findById(id).orElse(null);

        if (seguimientoExistente == null) {
            return null;
        }

        // Mantiene solo el ID de solicitud porque está en otro microservicio
        if (seguimiento.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudDTO solicitud = solicitudClient.getSolicitudById(seguimiento.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }
        } catch (FeignException error) {
            return null;
        }

        seguimientoExistente.setFechaSeguimiento(seguimiento.getFechaSeguimiento());
        seguimientoExistente.setObservacionSeguimiento(seguimiento.getObservacionSeguimiento().trim().toUpperCase());
        seguimientoExistente.setIdSolicitud(seguimiento.getIdSolicitud());

        return seguimientoRepository.save(seguimientoExistente);
    }

    public boolean eliminarSeguimiento(Integer id) {
        if (!seguimientoRepository.existsById(id)) {
            return false;
        }

        seguimientoRepository.deleteById(id);
        return true;
    }
}