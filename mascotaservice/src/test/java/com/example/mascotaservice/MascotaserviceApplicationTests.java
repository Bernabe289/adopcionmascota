package com.example.mascotaservice;

import com.example.mascotaservice.Client.RazaClient;
import com.example.mascotaservice.Client.RefugioClient;
import com.example.mascotaservice.Dto.RazaDTO;
import com.example.mascotaservice.Dto.RefugioDTO;
import com.example.mascotaservice.Model.Mascota;
import com.example.mascotaservice.Repository.MascotaRepository;
import com.example.mascotaservice.Service.MascotaService;
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
class MascotaserviceApplicationTests {

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private RazaClient razaClient;

    @Mock
    private RefugioClient refugioClient;

    @InjectMocks
    private MascotaService mascotaService;

    private Mascota mascota;
    private RazaDTO razaDTO;
    private RefugioDTO refugioDTO;

    @BeforeEach
    void setUp() {
        mascota = new Mascota();
        mascota.setIdMascota(1);
        mascota.setNombreMascota("Firulais");
        mascota.setEdadMascota(3);
        mascota.setSexoMascota("Macho");
        mascota.setTamanoMascota("Mediano");
        mascota.setEstadoMascota("Disponible");
        mascota.setDescripcionMascota("Mascota tranquila");
        mascota.setIdRaza(1);
        mascota.setIdRefugio(1);

        razaDTO = new RazaDTO();
        razaDTO.setIdraza(1);
        razaDTO.setNombreRaza("LABRADOR");
        razaDTO.setIdEspecie(1);

        refugioDTO = new RefugioDTO();
        refugioDTO.setIdRefugio(1);
        refugioDTO.setNombreRefugio("REFUGIO PATITAS");
        refugioDTO.setDireccionRefugio("Calle 123");
    }

    @Test
    @DisplayName("Buscar mascota por ID existente")
    void buscarMascotaPorIdExistente() {
        // Given
        when(mascotaRepository.findById(1)).thenReturn(Optional.of(mascota));

        // When
        Mascota resultado = mascotaService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdMascota());
        assertEquals("Firulais", resultado.getNombreMascota());
    }

    @Test
    @DisplayName("Buscar mascota por ID inexistente")
    void buscarMascotaPorIdInexistente() {
        // Given
        when(mascotaRepository.findById(99)).thenReturn(Optional.empty());

        // When
        Mascota resultado = mascotaService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar mascota correctamente con raza y refugio existentes")
    void guardarMascotaCorrectamenteConRazaYRefugioExistentes() {
        // Given
        when(razaClient.getRazaById(1)).thenReturn(razaDTO);
        when(refugioClient.getRefugioById(1)).thenReturn(refugioDTO);
        when(mascotaRepository.save(mascota)).thenReturn(mascota);

        // When
        Mascota resultado = mascotaService.guardarMascota(mascota);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdMascota());
        assertEquals("FIRULAIS", resultado.getNombreMascota());
        assertEquals("MACHO", resultado.getSexoMascota());
        assertEquals("MEDIANO", resultado.getTamanoMascota());
        assertEquals("DISPONIBLE", resultado.getEstadoMascota());
        assertEquals(1, resultado.getIdRaza());
        assertEquals(1, resultado.getIdRefugio());
    }

    @Test
    @DisplayName("No guardar mascota si la raza no existe")
    void noGuardarMascotaSiRazaNoExiste() {
        // Given
        when(razaClient.getRazaById(1)).thenReturn(null);

        // When
        Mascota resultado = mascotaService.guardarMascota(mascota);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar mascota si el refugio no existe")
    void noGuardarMascotaSiRefugioNoExiste() {
        // Given
        when(razaClient.getRazaById(1)).thenReturn(razaDTO);
        when(refugioClient.getRefugioById(1)).thenReturn(null);

        // When
        Mascota resultado = mascotaService.guardarMascota(mascota);

        // Then
        assertNull(resultado);
    }
}
