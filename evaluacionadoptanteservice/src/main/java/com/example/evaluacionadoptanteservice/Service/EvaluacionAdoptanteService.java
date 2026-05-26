package com.example.evaluacionadoptanteservice.Service;

import com.example.evaluacionadoptanteservice.Client.SolicitudAdopcionClient;
import com.example.evaluacionadoptanteservice.Dto.SolicitudAdopcionDTO;
import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import com.example.evaluacionadoptanteservice.Repository.EvaluacionAdoptanteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionAdoptanteService {

    @Autowired
    private EvaluacionAdoptanteRepository evaluacionAdoptanteRepository;

    @Autowired
    private SolicitudAdopcionClient solicitudAdopcionClient;

    public List<EvaluacionAdoptante> listarEvaluaciones() {
        return evaluacionAdoptanteRepository.findAll();
    }

    public EvaluacionAdoptante guardarEvaluacion(EvaluacionAdoptante evaluacionAdoptante) {
        if (evaluacionAdoptante.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(evaluacionAdoptante.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;

        }
        evaluacionAdoptante.setResultadoEvaluacion(evaluacionAdoptante.getResultadoEvaluacion().trim().toUpperCase());

        if (evaluacionAdoptante.getObservacionEvaluacion() != null) {
            evaluacionAdoptante.setObservacionEvaluacion(evaluacionAdoptante.getObservacionEvaluacion().trim());
        }

        return evaluacionAdoptanteRepository.save(evaluacionAdoptante);
    }

    public EvaluacionAdoptante buscarPorId(Integer id) {
        return evaluacionAdoptanteRepository.findById(id).orElse(null);
    }

    public EvaluacionAdoptante actualizarEvaluacion(Integer id, EvaluacionAdoptante evaluacionAdoptante) {
        EvaluacionAdoptante evaluacionExistente = evaluacionAdoptanteRepository.findById(id).orElse(null);

        if (evaluacionExistente == null) {
            return null;
        }

        if (evaluacionAdoptante.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(evaluacionAdoptante.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }

        evaluacionExistente.setIdSolicitud(evaluacionAdoptante.getIdSolicitud());
        evaluacionExistente.setResultadoEvaluacion(evaluacionAdoptante.getResultadoEvaluacion().trim().toUpperCase());
        evaluacionExistente.setFechaEvaluacion(evaluacionAdoptante.getFechaEvaluacion());

        if (evaluacionAdoptante.getObservacionEvaluacion() != null) {
            evaluacionExistente.setObservacionEvaluacion(evaluacionAdoptante.getObservacionEvaluacion().trim());
        }

        return evaluacionAdoptanteRepository.save(evaluacionExistente);
    }

    public boolean eliminarEvaluacion(Integer id) {
        if (!evaluacionAdoptanteRepository.existsById(id)) {
            return false;
        }

        evaluacionAdoptanteRepository.deleteById(id);
        return true;
    }
}
