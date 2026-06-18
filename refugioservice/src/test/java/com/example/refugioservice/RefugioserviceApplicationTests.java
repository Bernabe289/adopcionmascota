package com.example.refugioservice;

import com.example.refugioservice.Model.Refugio;
import com.example.refugioservice.Repository.RefugioRepository;
import com.example.refugioservice.Service.RefugioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RefugioserviceApplicationTests {

    @Mock
    RefugioRepository refugioRepository;

    @InjectMocks
    RefugioService refugioService;

    private Refugio refugio;

    @BeforeEach
    void setUp(){
        refugio = new Refugio();
        refugio.setIdRefugio(1);
        refugio.setNombreRefugio("Refugio Patitas");
        refugio.setDireccionRefugio("Calle 1234");
        refugio.setTelefonoRefugio("987654321");
        refugio.setEmailRefugio("refugio@gmail.com");
        refugio.setEstadoRefugio("ACTIVO");
    }

    @Test
    @DisplayName("Buscar refugio por ID existente")
    void buscarRefugioPorIdExistente(){
        //Given
        when(refugioRepository.findById(1)).thenReturn(Optional.of(refugio));

        Refugio resultado = refugioService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRefugio());
        assertEquals("Refugio Patitas", resultado.getNombreRefugio());
    }

    @Test
    @DisplayName("Buscar refugio por ID inexistente")
    void buscarRefugioPorIdInexistente() {
        //Given
        when(refugioRepository.findById(99)).thenReturn(Optional.empty());

        //When
        Refugio resultado = refugioService.buscarPorId(99);

        //Then
        assertNull(resultado);
    }
    @Test
    @DisplayName("Guardar refugio")
    void guardarRefugio() {
        //Given
        when(refugioRepository.save(refugio)).thenReturn(refugio);

        //When
        Refugio resultado = refugioService.guardarRefugio(refugio);

        //Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRefugio());
        assertTrue(resultado.getNombreRefugio().equalsIgnoreCase("Refugio Patitas"));
        assertEquals("refugio@gmail.com", resultado.getEmailRefugio());
    }
}
