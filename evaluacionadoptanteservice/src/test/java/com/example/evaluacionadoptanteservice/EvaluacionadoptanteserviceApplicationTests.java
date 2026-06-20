package com.example.evaluacionadoptanteservice;

import com.example.evaluacionadoptanteservice.Client.SolicitudAdopcionClient;
import com.example.evaluacionadoptanteservice.Dto.SolicitudAdopcionDTO;
import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import com.example.evaluacionadoptanteservice.Repository.EvaluacionAdoptanteRepository;
import com.example.evaluacionadoptanteservice.Service.EvaluacionAdoptanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluacionadoptanteserviceApplicationTests {

    @Mock
    EvaluacionAdoptanteRepository evaluacionAdoptanteRepository;

    @Mock
    SolicitudAdopcionClient solicitudAdopcionClient;

    @InjectMocks
    EvaluacionAdoptanteService evaluacionAdoptanteService;

    private EvaluacionAdoptante evaluacionAdoptante;
    private SolicitudAdopcionDTO solicitudAdopcionDTO;

    @BeforeEach
    void setUp(){
        evaluacionAdoptante = new EvaluacionAdoptante();
        evaluacionAdoptante.setIdEvaluacion(1);
        evaluacionAdoptante.setIdSolicitud(1);
        evaluacionAdoptante.setResultadoEvaluacion("APROBADO");
        evaluacionAdoptante.setObservacionEvaluacion("Adoptante apto para adopción");
        evaluacionAdoptante.setFechaEvaluacion(LocalDate.of(2026,6,18));

        solicitudAdopcionDTO = new SolicitudAdopcionDTO();
        solicitudAdopcionDTO.setIdSolicitud(1);
    }
    @Test
    @DisplayName("Buscar evaluación por ID existente")
    void buscarEvaluacionPorIdExistente() {
        //Given
        when(evaluacionAdoptanteRepository.findById(1)).thenReturn(Optional.of(evaluacionAdoptante));

        //When
        EvaluacionAdoptante resultado = evaluacionAdoptanteService.buscarPorId(1);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvaluacion());
        assertEquals("APROBADO", resultado.getResultadoEvaluacion());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("Buscar evaluación por ID inexistente")
    void buscarEvaluacionPorIdInexistente() {
        //Given
        when(evaluacionAdoptanteRepository.findById(99)).thenReturn(Optional.empty());

        //When
        EvaluacionAdoptante resultado = evaluacionAdoptanteService.buscarPorId(99);

        //Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar evaluación correctamente con solicitud válida")
    void guardarEvaluacion() {
        //Given
        when(solicitudAdopcionClient.getSolicitudById(1)).thenReturn(solicitudAdopcionDTO);
        when(evaluacionAdoptanteRepository.save(evaluacionAdoptante)).thenReturn(evaluacionAdoptante);

        //When
        EvaluacionAdoptante resultado = evaluacionAdoptanteService.guardarEvaluacion(evaluacionAdoptante);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvaluacion());
        assertEquals("APROBADO", resultado.getResultadoEvaluacion());
        assertEquals(1, resultado.getIdSolicitud());
        assertEquals(LocalDate.of(2026, 6, 18), resultado.getFechaEvaluacion());
    }

    @Test
    @DisplayName("No guardar evaluación si la solicitud no existe")
    void noGuardarEvaluacion() {
        //Given
        when(solicitudAdopcionClient.getSolicitudById(1)).thenReturn(null);

        //When
        EvaluacionAdoptante resultado = evaluacionAdoptanteService.guardarEvaluacion(evaluacionAdoptante);

        //Then
        assertNull(resultado);
    }

}
