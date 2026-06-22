package com.example.visitaservice;

import com.example.visitaservice.Client.SolicitudClient;
import com.example.visitaservice.Dto.SolicitudDTO;
import com.example.visitaservice.Model.Visita;
import com.example.visitaservice.Repository.VisitaRepository;
import com.example.visitaservice.Service.VisitaService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitaserviceApplicationTests {

    @Mock
    private VisitaRepository visitaRepository;

    @Mock
    private SolicitudClient solicitudClient;

    @InjectMocks
    private VisitaService visitaService;

    private Visita visita;
    private SolicitudDTO solicitudDTO;

    @BeforeEach
    void setUp() {
        visita = new Visita();
        visita.setIdVisita(1);
        visita.setFechaVisita(LocalDate.of(2026, 6, 18));
        visita.setEstadoVisita("REALIZADA");
        visita.setIdSolicitud(1);

        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setIdSolicitud(1);
    }

    @Test
    @DisplayName("Buscar visita por ID existente")
    void buscarVisitaPorIdExistente() {
        // Given
        when(visitaRepository.findById(1)).thenReturn(Optional.of(visita));

        // When
        Visita resultado = visitaService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdVisita());
        assertEquals("REALIZADA", resultado.getEstadoVisita());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("Buscar visita por ID inexistente")
    void buscarVisitaPorIdInexistente() {
        // Given
        when(visitaRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Visita resultado = visitaService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar visita correctamente con solicitud existente")
    void guardarVisitaCorrectamenteConSolicitudExistente() {
        // Given
        when(solicitudClient.getSolicitudById(1)).thenReturn(solicitudDTO);
        when(visitaRepository.save(visita)).thenReturn(visita);

        // When
        Visita resultado = visitaService.guardarVisita(visita);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdVisita());
        assertEquals(LocalDate.of(2026, 6, 18), resultado.getFechaVisita());
        assertEquals("REALIZADA", resultado.getEstadoVisita());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("No guardar visita si la solicitud no existe")
    void noGuardarVisitaSiSolicitudNoExiste() {
        // Given
        when(solicitudClient.getSolicitudById(1)).thenReturn(null);

        // When
        Visita resultado = visitaService.guardarVisita(visita);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar visita si idSolicitud viene null")
    void noGuardarVisitaSiIdSolicitudEsNull() {
        // Given
        visita.setIdSolicitud(null);

        // When
        Visita resultado = visitaService.guardarVisita(visita);

        // Then
        assertNull(resultado);
    }
}