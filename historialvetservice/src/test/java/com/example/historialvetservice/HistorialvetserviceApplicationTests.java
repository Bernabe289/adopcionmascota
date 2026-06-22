package com.example.historialvetservice;

import com.example.historialvetservice.Client.MascotaClient;
import com.example.historialvetservice.Dto.MascotaDTO;
import com.example.historialvetservice.Model.HistorialVet;
import com.example.historialvetservice.Repository.HistorialVetRepository;
import com.example.historialvetservice.Service.HistorialVetService;
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
class HistorialvetserviceApplicationTests {

    @Mock
    private HistorialVetRepository historialVetRepository;

    @Mock
    private MascotaClient mascotaClient;

    @InjectMocks
    private HistorialVetService historialVetService;

    private HistorialVet historialVet;
    private MascotaDTO mascotaDTO;

    @BeforeEach
    void setUp() {
        historialVet = new HistorialVet();
        historialVet.setIdHistorial(1);
        historialVet.setDescripcionHistorial(" Control general ");
        historialVet.setFechaRegistroHistorial(LocalDate.now());
        historialVet.setIdMascota(1);

        mascotaDTO = new MascotaDTO();
        mascotaDTO.setIdMascota(1);
        mascotaDTO.setNombreMascota("FIRULAIS");
        mascotaDTO.setEdadMascota(3);
        mascotaDTO.setSexoMascota("MACHO");
        mascotaDTO.setTamanoMascota("MEDIANO");
        mascotaDTO.setEstadoMascota("DISPONIBLE");
        mascotaDTO.setDescripcionMascota("Mascota tranquila");
        mascotaDTO.setIdRaza(1);
        mascotaDTO.setIdRefugio(1);
    }

    @Test
    @DisplayName("Buscar historial por ID existente")
    void buscarHistorialPorIdExistente() {
        //Given
        when(historialVetRepository.findById(1)).thenReturn(Optional.of(historialVet));

        //When
        HistorialVet resultado = historialVetService.buscarPorId(1);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdHistorial());
        assertEquals(1, resultado.getIdMascota());
    }

    @Test
    @DisplayName("Buscar historial por ID inexistente")
    void buscarHistorialPorIdInexistente() {
        //Given
        when(historialVetRepository.findById(99)).thenReturn(Optional.empty());

        //When
        HistorialVet resultado = historialVetService.buscarPorId(99);

        //Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar historial correctamente con mascota existente")
    void guardarHistorialCorrectamenteConMascotaExistente() {
        //Given
        when(mascotaClient.getMascotaById(1)).thenReturn(mascotaDTO);
        when(historialVetRepository.save(historialVet)).thenReturn(historialVet);

        //When
        HistorialVet resultado = historialVetService.guardarHistorial(historialVet);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdHistorial());
        assertEquals(1, resultado.getIdMascota());
        assertEquals("CONTROL GENERAL", resultado.getDescripcionHistorial());
    }

    @Test
    @DisplayName("No guardar historial si la mascota no existe")
    void noGuardarHistorialSiMascotaNoExiste() {
        //Given
        when(mascotaClient.getMascotaById(1)).thenReturn(null);

        //When
        HistorialVet resultado = historialVetService.guardarHistorial(historialVet);

        //Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar historial si idMascota viene null")
    void noGuardarHistorialSiIdMascotaEsNull() {
        //Given
        historialVet.setIdMascota(null);

        //When
        HistorialVet resultado = historialVetService.guardarHistorial(historialVet);

        //Then
        assertNull(resultado);
    }
}
