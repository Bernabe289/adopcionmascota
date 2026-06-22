package com.example.seguimientoservice;

import com.example.seguimientoservice.Client.SolicitudClient;
import com.example.seguimientoservice.Dto.SolicitudDTO;
import com.example.seguimientoservice.Model.Seguimiento;
import com.example.seguimientoservice.Repository.SeguimientoRepository;
import com.example.seguimientoservice.Service.SeguimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeguimientoserviceApplicationTests {

    @Mock
    private SeguimientoRepository seguimientoRepository;

    @Mock
    private SolicitudClient solicitudClient;

    @InjectMocks
    private SeguimientoService seguimientoService;

    private Seguimiento seguimiento;
    private SolicitudDTO solicitudDTO;

    @BeforeEach
    void setUp() {
        seguimiento = new Seguimiento();
        seguimiento.setIdSeguimiento(1);
        seguimiento.setFechaSeguimiento(LocalDate.of(2026, 6, 18));
        seguimiento.setObservacionSeguimiento("MASCOTA EN BUEN ESTADO");
        seguimiento.setIdSolicitud(1);

        solicitudDTO = new SolicitudDTO();
        solicitudDTO.setIdSolicitud(1);
    }

    @Test
    @DisplayName("Listar seguimientos")
    void listarSeguimientos() {
        // Given
        when(seguimientoRepository.findAll()).thenReturn(List.of(seguimiento));

        // When
        List<Seguimiento> resultado = seguimientoService.listarSeguimientos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdSeguimiento());
        assertEquals("MASCOTA EN BUEN ESTADO", resultado.get(0).getObservacionSeguimiento());
        assertEquals(1, resultado.get(0).getIdSolicitud());
    }

    @Test
    @DisplayName("Buscar seguimiento por ID existente")
    void buscarSeguimientoPorIdExistente() {
        // Given
        when(seguimientoRepository.findById(1)).thenReturn(Optional.of(seguimiento));

        // When
        Seguimiento resultado = seguimientoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSeguimiento());
        assertEquals("MASCOTA EN BUEN ESTADO", resultado.getObservacionSeguimiento());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("Buscar seguimiento por ID inexistente")
    void buscarSeguimientoPorIdInexistente() {
        // Given
        when(seguimientoRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Seguimiento resultado = seguimientoService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar seguimiento correctamente con solicitud existente")
    void guardarSeguimientoCorrectamenteConSolicitudExistente() {
        // Given
        when(solicitudClient.getSolicitudById(1)).thenReturn(solicitudDTO);
        when(seguimientoRepository.save(seguimiento)).thenReturn(seguimiento);

        // When
        Seguimiento resultado = seguimientoService.guardarSeguimiento(seguimiento);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSeguimiento());
        assertEquals(LocalDate.of(2026, 6, 18), resultado.getFechaSeguimiento());
        assertEquals("MASCOTA EN BUEN ESTADO", resultado.getObservacionSeguimiento());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("No guardar seguimiento si la solicitud no existe")
    void noGuardarSeguimientoSiSolicitudNoExiste() {
        // Given
        when(solicitudClient.getSolicitudById(1)).thenReturn(null);

        // When
        Seguimiento resultado = seguimientoService.guardarSeguimiento(seguimiento);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Actualizar seguimiento correctamente")
    void actualizarSeguimientoCorrectamente() {
        // Given
        Seguimiento seguimientoActualizado = new Seguimiento();
        seguimientoActualizado.setFechaSeguimiento(LocalDate.of(2026, 6, 20));
        seguimientoActualizado.setObservacionSeguimiento("MASCOTA ADAPTADA CORRECTAMENTE");
        seguimientoActualizado.setIdSolicitud(1);

        when(seguimientoRepository.findById(1)).thenReturn(Optional.of(seguimiento));
        when(solicitudClient.getSolicitudById(1)).thenReturn(solicitudDTO);
        when(seguimientoRepository.save(seguimiento)).thenReturn(seguimiento);

        // When
        Seguimiento resultado = seguimientoService.actualizarSeguimiento(1, seguimientoActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSeguimiento());
        assertEquals(LocalDate.of(2026, 6, 20), resultado.getFechaSeguimiento());
        assertEquals("MASCOTA ADAPTADA CORRECTAMENTE", resultado.getObservacionSeguimiento());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("No actualizar seguimiento si no existe")
    void noActualizarSeguimientoSiNoExiste() {
        // Given
        when(seguimientoRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Seguimiento resultado = seguimientoService.actualizarSeguimiento(99, seguimiento);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No actualizar seguimiento si la solicitud no existe")
    void noActualizarSeguimientoSiSolicitudNoExiste() {
        // Given
        when(seguimientoRepository.findById(1)).thenReturn(Optional.of(seguimiento));
        when(solicitudClient.getSolicitudById(1)).thenReturn(null);

        // When
        Seguimiento resultado = seguimientoService.actualizarSeguimiento(1, seguimiento);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Eliminar seguimiento existente")
    void eliminarSeguimientoExistente() {
        // Given
        when(seguimientoRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = seguimientoService.eliminarSeguimiento(1);

        // Then
        assertTrue(resultado);
    }

    @Test
    @DisplayName("No eliminar seguimiento inexistente")
    void noEliminarSeguimientoInexistente() {
        // Given
        when(seguimientoRepository.existsById(99)).thenReturn(false);

        // When
        boolean resultado = seguimientoService.eliminarSeguimiento(99);

        // Then
        assertFalse(resultado);
    }
}