package com.example.historialvetservice.Service;

import com.example.historialvetservice.Model.HistorialVet;
import com.example.historialvetservice.Repository.HistorialVetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialVetService {

    @Autowired
    private HistorialVetRepository historialVetRepository;

    public List<HistorialVet> listarHistoriales() {
        return historialVetRepository.findAll();
    }

    public HistorialVet guardarHistorial(HistorialVet historialVet) {

        // Valida que el historial esté asociado a una mascota
        if (historialVet.getIdMascota() == null) {
            return null;
        }

        historialVet.setDescripcionHistorial(historialVet.getDescripcionHistorial().trim().toUpperCase());

        return historialVetRepository.save(historialVet);
    }

    public HistorialVet buscarPorId(Integer id) {
        return historialVetRepository.findById(id).orElse(null);
    }

    public HistorialVet actualizarHistorial(Integer id, HistorialVet historialVet) {
        HistorialVet historialExistente = historialVetRepository.findById(id).orElse(null);

        if (historialExistente == null) {
            return null;
        }

        // Mantiene solo el ID de mascota porque Mascota está en otro microservicio
        if (historialVet.getIdMascota() == null) {
            return null;
        }

        historialExistente.setDescripcionHistorial(historialVet.getDescripcionHistorial().trim().toUpperCase());
        historialExistente.setFechaRegistroHistorial(historialVet.getFechaRegistroHistorial());
        historialExistente.setIdMascota(historialVet.getIdMascota());

        return historialVetRepository.save(historialExistente);
    }

    public boolean eliminarHistorial(Integer id) {
        if (!historialVetRepository.existsById(id)) {
            return false;
        }

        historialVetRepository.deleteById(id);
        return true;
    }
}