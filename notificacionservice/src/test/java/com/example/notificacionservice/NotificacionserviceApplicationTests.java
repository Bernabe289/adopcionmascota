package com.example.notificacionservice;

import com.example.notificacionservice.Client.UsuarioClient;
import com.example.notificacionservice.Dto.UsuarioDTO;
import com.example.notificacionservice.Model.Notificacion;
import com.example.notificacionservice.Repository.NotificacionRepository;
import com.example.notificacionservice.Service.NotificacionService;
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
class NotificacionserviceApplicationTests {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacion;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        notificacion = new Notificacion();
        notificacion.setIdNotificacion(1);
        notificacion.setMensajeNotificacion("ADOPCION APROBADA");
        notificacion.setFechaNotificacion(LocalDate.of(2026, 6, 18));
        notificacion.setEstadoNotificacion("ENVIADA");
        notificacion.setIdUsuario(1);

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(1);
    }

    @Test
    @DisplayName("Listar notificaciones")
    void listarNotificaciones() {
        // Given
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacion));

        // When
        List<Notificacion> resultado = notificacionService.listarNotificaciones();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdNotificacion());
        assertEquals("ADOPCION APROBADA", resultado.get(0).getMensajeNotificacion());
        assertEquals("ENVIADA", resultado.get(0).getEstadoNotificacion());
        assertEquals(1, resultado.get(0).getIdUsuario());
    }

    @Test
    @DisplayName("Buscar notificación por ID existente")
    void buscarNotificacionPorIdExistente() {
        // Given
        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacion));

        // When
        Notificacion resultado = notificacionService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdNotificacion());
        assertEquals("ADOPCION APROBADA", resultado.getMensajeNotificacion());
        assertEquals("ENVIADA", resultado.getEstadoNotificacion());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("Buscar notificación por ID inexistente")
    void buscarNotificacionPorIdInexistente() {
        // Given
        when(notificacionRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Notificacion resultado = notificacionService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar notificación correctamente con usuario existente")
    void guardarNotificacionCorrectamenteConUsuarioExistente() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);

        // When
        Notificacion resultado = notificacionService.guardarNotificacion(notificacion);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdNotificacion());
        assertEquals("ADOPCION APROBADA", resultado.getMensajeNotificacion());
        assertEquals(LocalDate.of(2026, 6, 18), resultado.getFechaNotificacion());
        assertEquals("ENVIADA", resultado.getEstadoNotificacion());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("No guardar notificación si el usuario no existe")
    void noGuardarNotificacionSiUsuarioNoExiste() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(null);

        // When
        Notificacion resultado = notificacionService.guardarNotificacion(notificacion);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Actualizar notificación correctamente")
    void actualizarNotificacionCorrectamente() {
        // Given
        Notificacion notificacionActualizada = new Notificacion();
        notificacionActualizada.setMensajeNotificacion("VISITA PROGRAMADA");
        notificacionActualizada.setFechaNotificacion(LocalDate.of(2026, 6, 20));
        notificacionActualizada.setEstadoNotificacion("PENDIENTE");
        notificacionActualizada.setIdUsuario(1);

        when(notificacionRepository.findById(1)).thenReturn(Optional.of(notificacion));
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(notificacionRepository.save(notificacion)).thenReturn(notificacion);

        // When
        Notificacion resultado = notificacionService.actualizarNotificacion(1, notificacionActualizada);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdNotificacion());
        assertEquals("VISITA PROGRAMADA", resultado.getMensajeNotificacion());
        assertEquals(LocalDate.of(2026, 6, 20), resultado.getFechaNotificacion());
        assertEquals("PENDIENTE", resultado.getEstadoNotificacion());
        assertEquals(1, resultado.getIdUsuario());
    }

    @Test
    @DisplayName("Eliminar notificación existente")
    void eliminarNotificacionExistente() {
        // Given
        when(notificacionRepository.existsById(1)).thenReturn(true);

        // When
        boolean resultado = notificacionService.eliminarNotificacion(1);

        // Then
        assertTrue(resultado);
    }
}