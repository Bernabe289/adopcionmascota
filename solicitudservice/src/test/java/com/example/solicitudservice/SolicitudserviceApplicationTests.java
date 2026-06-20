package com.example.solicitudservice;

import com.example.solicitudservice.Client.MascotaClient;
import com.example.solicitudservice.Client.UsuarioClient;
import com.example.solicitudservice.Dto.MascotaDTO;
import com.example.solicitudservice.Dto.UsuarioDTO;
import com.example.solicitudservice.Model.SolicitudAdopcion;
import com.example.solicitudservice.Repository.SolicitudAdopcionRepository;
import com.example.solicitudservice.Service.SolicitudAdopcionService;
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
class SolicitudserviceApplicationTests {

    @Mock
    private SolicitudAdopcionRepository solicitudAdopcionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private SolicitudAdopcionService solicitudAdopcionService;

    private SolicitudAdopcion solicitudAdopcion;
    private UsuarioDTO usuarioDTO;
    private MascotaDTO mascotaDTO;

    @BeforeEach
    void setUp() {
        solicitudAdopcion = new SolicitudAdopcion();
        solicitudAdopcion.setIdSolicitud(1);
        solicitudAdopcion.setIdUsuario(1);
        solicitudAdopcion.setIdMascota(1);
        solicitudAdopcion.setFechaSolicitud(LocalDate.now());
        solicitudAdopcion.setEstadoSolicitud("Pendiente");
        solicitudAdopcion.setObservacionSolicitud(" Quiere adoptar ");

        usuarioDTO = new UsuarioDTO();
        mascotaDTO = new MascotaDTO();
    }

    @Test
    @DisplayName("Buscar solicitud por ID existente")
    void buscarSolicitudPorIdExistente() {
        // Given
        when(solicitudAdopcionRepository.findById(1)).thenReturn(Optional.of(solicitudAdopcion));

        // When
        SolicitudAdopcion resultado = solicitudAdopcionService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSolicitud());
        assertEquals(1, resultado.getIdUsuario());
        assertEquals(1, resultado.getIdMascota());
    }

    @Test
    @DisplayName("Buscar solicitud por ID inexistente")
    void buscarSolicitudPorIdInexistente() {
        // Given
        when(solicitudAdopcionRepository.findById(99)).thenReturn(Optional.empty());

        // When
        SolicitudAdopcion resultado = solicitudAdopcionService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar solicitud correctamente con usuario y mascota existentes")
    void guardarSolicitudCorrectamenteConUsuarioYMascotaExistentes() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(mascotaClient.getMascotaById(1)).thenReturn(mascotaDTO);
        when(solicitudAdopcionRepository.save(solicitudAdopcion)).thenReturn(solicitudAdopcion);

        // When
        SolicitudAdopcion resultado = solicitudAdopcionService.guardarSolicitud(solicitudAdopcion);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdSolicitud());
        assertEquals(1, resultado.getIdUsuario());
        assertEquals(1, resultado.getIdMascota());
        assertEquals("PENDIENTE", resultado.getEstadoSolicitud());
        assertEquals("Quiere adoptar", resultado.getObservacionSolicitud());
    }

    @Test
    @DisplayName("No guardar solicitud si el usuario no existe")
    void noGuardarSolicitudSiUsuarioNoExiste() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(null);

        // When
        SolicitudAdopcion resultado = solicitudAdopcionService.guardarSolicitud(solicitudAdopcion);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar solicitud si la mascota no existe")
    void noGuardarSolicitudSiMascotaNoExiste() {
        // Given
        when(usuarioClient.getUsuarioById(1)).thenReturn(usuarioDTO);
        when(mascotaClient.getMascotaById(1)).thenReturn(null);

        // When
        SolicitudAdopcion resultado = solicitudAdopcionService.guardarSolicitud(solicitudAdopcion);

        // Then
        assertNull(resultado);
    }
}
