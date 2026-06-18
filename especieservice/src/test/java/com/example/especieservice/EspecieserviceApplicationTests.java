package com.example.especieservice;

import com.example.especieservice.Model.Especie;
import com.example.especieservice.Repository.EspecieRepository;
import com.example.especieservice.Service.EspecieService;
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
class EspecieserviceApplicationTests {

    @Mock
    EspecieRepository especieRepository;

    @InjectMocks
    EspecieService especieService;

    private Especie especie;

    @BeforeEach
    void setUp(){
        especie = new Especie();
        especie.setIdEspecie(1);
        especie.setNombreEspecie("PERRO");
    }

    @Test
    @DisplayName("Buscar especie por ID existente")
    void buscarEspeciePorIdExistente(){
        //Given
        when(especieRepository.findById(1)).thenReturn(Optional.of(especie));

        //When
        Especie resultado = especieService.buscarPorId(1);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEspecie());
        assertEquals("PERRO", resultado.getNombreEspecie());
    }

    @Test
    @DisplayName("Buscar especie por ID inexistente")
    void buscarEspeciePorIdInexistente(){
        //Given
        when(especieRepository.findById(99)).thenReturn(Optional.empty());

        //When
        Especie resultado = especieService.buscarPorId(99);

        //Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar especie correctamente")
    void guardarEspecieCorrectamente() {
        //Given
        when(especieRepository.save(especie)).thenReturn(especie);

        //When
        Especie resultado = especieService.guardarEspecie(especie);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEspecie());
        assertEquals("PERRO", resultado.getNombreEspecie());
    }
}
