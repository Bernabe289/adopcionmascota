package com.example.vacunaservice.Service;

import com.example.vacunaservice.Client.HistorialVetClient;
import com.example.vacunaservice.Dto.HistorialVetDTO;
import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Repository.VacunaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class VacunaService {

        private static final Logger logger = LoggerFactory.getLogger(VacunaService.class);

        @Autowired
        private VacunaRepository vacunaRepository;

        @Autowired
        private HistorialVetClient historialVetClient;

        public List<Vacuna> listarVacunas() {
                logger.info("Listando vacunas");
                return vacunaRepository.findAll();
        }

        public Vacuna guardarVacuna(Vacuna vacuna) {

                // Valida que la vacuna esté asociada a un historial veterinario
                if (vacuna.getIdHistorial() == null) {
                        logger.warn("No se pudo crear la vacuna: idHistorial viene null");
                        return null;
                }

                try {
                        HistorialVetDTO historial = historialVetClient.getHistorialById(vacuna.getIdHistorial());

                        if (historial == null) {
                                logger.warn("No se pudo crear la vacuna: historial ID {} no existe", vacuna.getIdHistorial());
                                return null;
                        }
                } catch (FeignException error) {
                        logger.warn("No se pudo crear la vacuna: error al consultar historial ID {}", vacuna.getIdHistorial());
                        return null;
                }

                vacuna.setNombreVacuna(vacuna.getNombreVacuna().trim().toUpperCase());

                Vacuna vacunaGuardada = vacunaRepository.save(vacuna);
                logger.info("Vacuna creada correctamente con ID {}", vacunaGuardada.getIdVacuna());

                return vacunaGuardada;
        }

        public Vacuna buscarPorId(Integer id) {
                logger.info("Buscando vacuna con ID {}", id);
                return vacunaRepository.findById(id).orElse(null);
        }

        public Vacuna actualizarVacuna(Integer id, Vacuna vacuna) {
                Vacuna vacunaExistente = vacunaRepository.findById(id).orElse(null);

                if (vacunaExistente == null) {
                        logger.warn("No se pudo actualizar la vacuna: no existe vacuna con ID {}", id);
                        return null;
                }

                // Mantiene solo el ID del historial porque está en otro microservicio
                if (vacuna.getIdHistorial() == null) {
                        logger.warn("No se pudo actualizar la vacuna ID {}: idHistorial viene null", id);
                        return null;
                }

                try {
                        HistorialVetDTO historial = historialVetClient.getHistorialById(vacuna.getIdHistorial());

                        if (historial == null) {
                                logger.warn("No se pudo actualizar la vacuna ID {}: historial ID {} no existe", id, vacuna.getIdHistorial());
                                return null;
                        }
                } catch (FeignException error) {
                        logger.warn("No se pudo actualizar la vacuna ID {}: error al consultar historial ID {}", id, vacuna.getIdHistorial());
                        return null;
                }

                vacunaExistente.setNombreVacuna(vacuna.getNombreVacuna().trim().toUpperCase());
                vacunaExistente.setFechaVacuna(vacuna.getFechaVacuna());
                vacunaExistente.setIdHistorial(vacuna.getIdHistorial());

                Vacuna vacunaActualizada = vacunaRepository.save(vacunaExistente);
                logger.info("Vacuna ID {} actualizada correctamente", vacunaActualizada.getIdVacuna());

                return vacunaActualizada;
        }

        public boolean eliminarVacuna(Integer id) {
                if (!vacunaRepository.existsById(id)) {
                        logger.warn("No se pudo eliminar la vacuna: no existe vacuna con ID {}", id);
                        return false;
                }

                vacunaRepository.deleteById(id);
                logger.info("Vacuna ID {} eliminada correctamente", id);

                return true;
        }
}
