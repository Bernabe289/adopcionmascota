package com.example.visitaservice.Service;

import com.example.visitaservice.Model.Visita;
import com.example.visitaservice.Repository.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    public List<Visita> listarVisitas() {
        return visitaRepository.findAll();
    }

    public Visita guardarVisita(Visita visita) {

        // Valida que la visita esté asociada a una solicitud
        if (visita.getIdSolicitud() == null) {
            return null;
        }

        visita.setEstadoVisita(visita.getEstadoVisita().trim().toUpperCase());

        return visitaRepository.save(visita);
    }

    public Visita buscarPorId(Integer id) {
        return visitaRepository.findById(id).orElse(null);
    }

    public Visita actualizarVisita(Integer id, Visita visita) {
        Visita visitaExistente = visitaRepository.findById(id).orElse(null);

        if (visitaExistente == null) {
            return null;
        }

        // Mantiene solo el ID de solicitud porque está en otro microservicio
        if (visita.getIdSolicitud() == null) {
            return null;
        }

        visitaExistente.setFechaVisita(visita.getFechaVisita());
        visitaExistente.setEstadoVisita(visita.getEstadoVisita().trim().toUpperCase());
        visitaExistente.setIdSolicitud(visita.getIdSolicitud());

        return visitaRepository.save(visitaExistente);
    }

    public boolean eliminarVisita(Integer id) {
        if (!visitaRepository.existsById(id)) {
            return false;
        }

        visitaRepository.deleteById(id);
        return true;
    }
}
