package com.example.razaservice;

import com.example.razaservice.Client.RazaClient;
import com.example.razaservice.Dto.EspecieDTO;
import com.example.razaservice.Model.Raza;
import com.example.razaservice.Repository.RazaRepository;
import com.example.razaservice.Service.RazaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RazaserviceApplicationTests {
    @Mock
    private RazaRepository razaRepository;

    @Mock
    private RazaClient razaClient;

    @InjectMocks
    private RazaService razaService;

    private Raza raza;
    private EspecieDTO especieDTO;

    @BeforeEach
    void setUp(){
        raza = new Raza();
        raza.setIdRaza(1);
        raza.setNombreRaza("Labrador");
        raza.setIdEspecie(1);

        especieDTO = new EspecieDTO();
        especieDTO.setIdEspecie(1);
        especieDTO.setNombreEspecie("PERRO");
    }
    @Test
    @DisplayName("Buscar raza por ID existente")
    void buscarRazaPorIdExistente(){
        //Given
        when(razaRepository.findById(1)).thenReturn(Optional.of(raza));
        //When
        Raza resultado = razaService.buscarPorId(1);
        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRaza());
        assertEquals("Labrador", resultado.getNombreRaza());
    }
    @Test
    @DisplayName("Buscar raza por ID inexistente")
    void buscarRazaPorIdInexistente(){
        //Given
        when(razaRepository.findById(99)).thenReturn(Optional.empty());
        //When
        Raza resultado = razaService.buscarPorId(99);
        //Then
        assertNull(resultado);
    }
    @Test
    @DisplayName("Guardar raza correctamente con especie existente")
    void guardarRazaCorrectamenteConEspecieExistente(){
        // Given
        when(razaRepository.existsByNombreRazaIgnoreCase("LABRADOR")).thenReturn(false);
        when(razaClient.getEspecieById(1)).thenReturn(especieDTO);
        when(razaRepository.save(raza)).thenReturn(raza);

        // When
        Raza resultado = razaService.guardarRaza(raza);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRaza());
        assertEquals("LABRADOR", resultado.getNombreRaza());
        assertEquals(1, resultado.getIdEspecie());
    }

    @Test
    @DisplayName("No guardar raza si la especie no existe")
    void noGuardarRazaSiEspecieNoExiste(){
        //Given
        when(razaRepository.existsByNombreRazaIgnoreCase("LABRADOR")).thenReturn(false);
        when(razaClient.getEspecieById(1)).thenReturn(null);
        //When
        Raza resultado = razaService.guardarRaza(raza);
        //Then
        assertNull(resultado);
    }
}
