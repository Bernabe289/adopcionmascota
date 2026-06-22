package com.example.reporteservice;

import com.example.reporteservice.Client.UsuarioClient;
import com.example.reporteservice.Dto.UsuarioDTO;
import com.example.reporteservice.Model.Reporte;
import com.example.reporteservice.Repository.ReporteRepository;
import com.example.reporteservice.Service.ReporteService;
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
class ReporteserviceApplicationTests {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ReporteService reporteService;

    private Reporte reporte;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        reporte = new Reporte();
        reporte.setIdReporte(1);
        reporte.setTipoReporte("ADOPCION");
        reporte.setDescripcionReporte("Reporte de adopcion realizada");
        reporte.setFechaReporte(LocalDate.of(2026, 6, 18));
        reporte.setEstadoReporte("GENERADO");
        reporte.setIdUsuario(1);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(1);
    }

    @Test
    @DisplayName("Listar reportes")
    void listarReportes() {
        // Given
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        // When
        List<Reporte> resultado = reporteService.listarReportes();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdReporte());
        assertEquals("ADOPCION", resultado.get(0).getTipoReporte());
        assertEquals("GENERADO", resultado.get(0).getEstadoReporte());
        assertEquals(1, resultado.get(0).getIdUsuario());
    }

    @Test
    @DisplayName("Buscar reporte por ID existente")
    void buscarReportePorIdExistente() {
        // Given
        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));

        // When
        Reporte resultado = reporteService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdReporte());
        assertEquals("ADOPCION", resultado.getTipoReporte());
        assertEquals("GENERADO", resultado.getEstadoReporte());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("Buscar reporte por ID inexistente")
    void buscarReportePorIdInexistente() {
        // Given
        when(reporteRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Reporte resultado = reporteService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar reporte correctamente con usuario existente")
    void guardarReporteCorrectamenteConUsuarioExistente() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(reporteRepository.save(reporte)).thenReturn(reporte);

        // When
        Reporte resultado = reporteService.guardarReporte(reporte);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdReporte());
        assertEquals("ADOPCION", resultado.getTipoReporte());
        assertEquals("Reporte de adopcion realizada", resultado.getDescripcionReporte());
        assertEquals(LocalDate.of(2026, 6, 18), resultado.getFechaReporte());
        assertEquals("GENERADO", resultado.getEstadoReporte());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("No guardar reporte si el usuario no existe")
    void noGuardarReporteSiUsuarioNoExiste() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(null);

        // When
        Reporte resultado = reporteService.guardarReporte(reporte);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Actualizar reporte correctamente")
    void actualizarReporteCorrectamente() {
        // Given
        Reporte reporteActualizado = new Reporte();
        reporteActualizado.setTipoReporte("SEGUIMIENTO");
        reporteActualizado.setDescripcionReporte("Reporte de seguimiento actualizado");
        reporteActualizado.setFechaReporte(LocalDate.of(2026, 6, 20));
        reporteActualizado.setEstadoReporte("ACTUALIZADO");
        reporteActualizado.setIdUsuario(1);

        when(reporteRepository.findById(1)).thenReturn(Optional.of(reporte));
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(reporteRepository.save(reporte)).thenReturn(reporte);

        // When
        Reporte resultado = reporteService.actualizarReporte(1, reporteActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdReporte());
        assertEquals("SEGUIMIENTO", resultado.getTipoReporte());
        assertEquals("Reporte de seguimiento actualizado", resultado.getDescripcionReporte());
        assertEquals(LocalDate.of(2026, 6, 20), resultado.getFechaReporte());
        assertEquals("ACTUALIZADO", resultado.getEstadoReporte());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("Eliminar reporte existente")
    void eliminarReporteExistente() {
        // Given
        when(reporteRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = reporteService.eliminarReporte(1);

        // Then
        assertTrue(resultado);
    }
}