package com.example.vacunaservice.Service;

import com.example.vacunaservice.Client.HistorialVetClient;
import com.example.vacunaservice.Dto.HistorialVetDTO;
import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Repository.VacunaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacunaService {

        @Autowired
        private VacunaRepository vacunaRepository;

        @Autowired
        private HistorialVetClient historialVetClient;

        public List<Vacuna> listarVacunas() {
                return vacunaRepository.findAll();
        }

        public Vacuna guardarVacuna(Vacuna vacuna) {

                // Valida que la vacuna esté asociada a un historial veterinario
                if (vacuna.getIdHistorial() == null) {
                        return null;
                }

                try {
                        HistorialVetDTO historial = historialVetClient.getHistorialById(vacuna.getIdHistorial());

                        if (historial == null) {
                                return null;
                        }
                } catch (FeignException error) {
                        return null;
                }

                vacuna.setNombreVacuna(vacuna.getNombreVacuna().trim().toUpperCase());

                return vacunaRepository.save(vacuna);
        }

        public Vacuna buscarPorId(Integer id) {
                return vacunaRepository.findById(id).orElse(null);
        }

        public Vacuna actualizarVacuna(Integer id, Vacuna vacuna) {
                Vacuna vacunaExistente = vacunaRepository.findById(id).orElse(null);

                if (vacunaExistente == null) {
                        return null;
                }

                // Mantiene solo el ID del historial porque está en otro microservicio
                if (vacuna.getIdHistorial() == null) {
                        return null;
                }

                try {
                        HistorialVetDTO historial = historialVetClient.getHistorialById(vacuna.getIdHistorial());

                        if (historial == null) {
                                return null;
                        }
                } catch (FeignException error) {
                        return null;
                }

                vacunaExistente.setNombreVacuna(vacuna.getNombreVacuna().trim().toUpperCase());
                vacunaExistente.setFechaVacuna(vacuna.getFechaVacuna());
                vacunaExistente.setIdHistorial(vacuna.getIdHistorial());

                return vacunaRepository.save(vacunaExistente);
        }

        public boolean eliminarVacuna(Integer id) {
                if (!vacunaRepository.existsById(id)) {
                        return false;
                }

                vacunaRepository.deleteById(id);
                return true;
        }
}
