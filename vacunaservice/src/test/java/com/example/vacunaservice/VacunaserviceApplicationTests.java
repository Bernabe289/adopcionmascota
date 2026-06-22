package com.example.vacunaservice;

import com.example.vacunaservice.Client.HistorialVetClient;
import com.example.vacunaservice.Dto.HistorialVetDTO;
import com.example.vacunaservice.Model.Vacuna;
import com.example.vacunaservice.Repository.VacunaRepository;
import com.example.vacunaservice.Service.VacunaService;
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
class VacunaserviceApplicationTests {

    @Mock
    private VacunaRepository vacunaRepository;

    @Mock
    private HistorialVetClient historialVetClient;

    @InjectMocks
    private VacunaService vacunaService;

    private Vacuna vacuna;
    private HistorialVetDTO historialVetDTO;

    @BeforeEach
    void setUp() {
        vacuna = new Vacuna();
        vacuna.setIdVacuna(1);
        vacuna.setNombreVacuna(" Antirrabica ");
        vacuna.setFechaVacuna(LocalDate.now());
        vacuna.setIdHistorial(1);

        historialVetDTO = new HistorialVetDTO();
        historialVetDTO.setIdHistorial(1);
        historialVetDTO.setDescripcionHistorial("CONTROL GENERAL");
        historialVetDTO.setFechaRegistroHistorial(LocalDate.now());
        historialVetDTO.setIdMascota(1);
    }

    @Test
    @DisplayName("Buscar vacuna por ID existente")
    void buscarVacunaPorIdExistente() {
// Given
        when(vacunaRepository.findById(1)).thenReturn(Optional.of(vacuna));

// When
        Vacuna resultado = vacunaService.buscarPorId(1);

// Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdVacuna());
        assertEquals(1, resultado.getIdHistorial());
    }

    @Test
    @DisplayName("Buscar vacuna por ID inexistente")
    void buscarVacunaPorIdInexistente() {
// Given
        when(vacunaRepository.findById(99)).thenReturn(Optional.empty());

// When
        Vacuna resultado = vacunaService.buscarPorId(99);

// Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar vacuna correctamente con historial existente")
    void guardarVacunaCorrectamenteConHistorialExistente() {
// Given
        when(historialVetClient.getHistorialById(1)).thenReturn(historialVetDTO);
        when(vacunaRepository.save(vacuna)).thenReturn(vacuna);

// When
        Vacuna resultado = vacunaService.guardarVacuna(vacuna);

// Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdVacuna());
        assertEquals(1, resultado.getIdHistorial());
        assertEquals("ANTIRRABICA", resultado.getNombreVacuna());
    }

    @Test
    @DisplayName("No guardar vacuna si el historial no existe")
    void noGuardarVacunaSiHistorialNoExiste() {
// Given
        when(historialVetClient.getHistorialById(1)).thenReturn(null);

// When
        Vacuna resultado = vacunaService.guardarVacuna(vacuna);

// Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar vacuna si idHistorial viene null")
    void noGuardarVacunaSiIdHistorialEsNull() {
// Given
        vacuna.setIdHistorial(null);

// When
        Vacuna resultado = vacunaService.guardarVacuna(vacuna);

// Then
        assertNull(resultado);
    }
}
