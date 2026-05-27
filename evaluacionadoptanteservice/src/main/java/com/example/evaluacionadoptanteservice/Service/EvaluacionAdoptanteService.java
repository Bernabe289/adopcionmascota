package com.example.evaluacionadoptanteservice.Service;

import com.example.evaluacionadoptanteservice.Client.SolicitudAdopcionClient;
import com.example.evaluacionadoptanteservice.Dto.SolicitudAdopcionDTO;
import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import com.example.evaluacionadoptanteservice.Repository.EvaluacionAdoptanteRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EvaluacionAdoptanteService {

    @Autowired
    private EvaluacionAdoptanteRepository evaluacionAdoptanteRepository;

    @Autowired
    private SolicitudAdopcionClient solicitudAdopcionClient;

    public List<EvaluacionAdoptante> listarEvaluaciones() {
        log.info("Listando evaluaciones de adoptantes");
        return evaluacionAdoptanteRepository.findAll();
    }

    public EvaluacionAdoptante guardarEvaluacion(EvaluacionAdoptante evaluacionAdoptante) {
        if (evaluacionAdoptante.getIdSolicitud() == null) {
            log.warn("No se pudo crear la evaluación: idSolicitud viene null");
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(evaluacionAdoptante.getIdSolicitud());

            if (solicitud == null) {
                log.warn("No se pudo crear la evaluación: solicitud ID {} no existe", evaluacionAdoptante.getIdSolicitud());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear la evaluación: solicitud ID {} no existe", evaluacionAdoptante.getIdSolicitud());
            return null;
        }

        evaluacionAdoptante.setResultadoEvaluacion(evaluacionAdoptante.getResultadoEvaluacion().trim().toUpperCase());

        if (evaluacionAdoptante.getObservacionEvaluacion() != null) {
            evaluacionAdoptante.setObservacionEvaluacion(evaluacionAdoptante.getObservacionEvaluacion().trim());
        }

        EvaluacionAdoptante evaluacionGuardada = evaluacionAdoptanteRepository.save(evaluacionAdoptante);

        log.info("Evaluación creada correctamente con ID {} para solicitud ID {}",
                evaluacionGuardada.getIdEvaluacion(),
                evaluacionGuardada.getIdSolicitud());

        return evaluacionGuardada;
    }

    public EvaluacionAdoptante buscarPorId(Integer id) {
        log.info("Buscando evaluación de adoptante con ID {}", id);
        return evaluacionAdoptanteRepository.findById(id).orElse(null);
    }

    public EvaluacionAdoptante actualizarEvaluacion(Integer id, EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante evaluacionExistente = evaluacionAdoptanteRepository.findById(id).orElse(null);

        if (evaluacionExistente == null) {
            log.warn("No se pudo actualizar la evaluación: no existe evaluación con ID {}", id);
            return null;
        }

        if (evaluacionAdoptante.getIdSolicitud() == null) {
            log.warn("No se pudo actualizar la evaluación ID {}: idSolicitud viene null", id);
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(evaluacionAdoptante.getIdSolicitud());

            if (solicitud == null) {
                log.warn("No se pudo actualizar la evaluación ID {}: solicitud ID {} no existe",
                        id, evaluacionAdoptante.getIdSolicitud());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar la evaluación ID {}: solicitud ID {} no existe",
                    id, evaluacionAdoptante.getIdSolicitud());
            return null;
        }

        evaluacionExistente.setIdSolicitud(evaluacionAdoptante.getIdSolicitud());
        evaluacionExistente.setResultadoEvaluacion(evaluacionAdoptante.getResultadoEvaluacion().trim().toUpperCase());
        evaluacionExistente.setFechaEvaluacion(evaluacionAdoptante.getFechaEvaluacion());

        if (evaluacionAdoptante.getObservacionEvaluacion() != null) {
            evaluacionExistente.setObservacionEvaluacion(evaluacionAdoptante.getObservacionEvaluacion().trim());
        }

        EvaluacionAdoptante evaluacionActualizada = evaluacionAdoptanteRepository.save(evaluacionExistente);

        log.info("Evaluación actualizada correctamente con ID {}", evaluacionActualizada.getIdEvaluacion());

        return evaluacionActualizada;
    }

    public boolean eliminarEvaluacion(Integer id) {
        if (!evaluacionAdoptanteRepository.existsById(id)) {
            log.warn("No se pudo eliminar la evaluación: no existe evaluación con ID {}", id);
            return false;
        }

        evaluacionAdoptanteRepository.deleteById(id);
        log.info("Evaluación eliminada correctamente con ID {}", id);
        return true;
    }
}