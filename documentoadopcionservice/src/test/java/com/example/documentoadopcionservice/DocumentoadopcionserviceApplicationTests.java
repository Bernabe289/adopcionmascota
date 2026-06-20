package com.example.documentoadopcionservice;

import com.example.documentoadopcionservice.Client.SolicitudAdopcionClient;
import com.example.documentoadopcionservice.Dto.SolicitudAdopcionDTO;
import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import com.example.documentoadopcionservice.Repository.DocumentoAdopcionRepository;
import com.example.documentoadopcionservice.Service.DocumentoAdopcionService;
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
class DocumentoadopcionserviceApplicationTests {

    @Mock
    private DocumentoAdopcionRepository documentoAdopcionRepository;

    @Mock
    private SolicitudAdopcionClient solicitudAdopcionClient;

    @InjectMocks
    private DocumentoAdopcionService documentoAdopcionService;

    private DocumentoAdopcion documentoAdopcion;
    private SolicitudAdopcionDTO solicitudAdopcionDTO;

    @BeforeEach
    void setUp() {
        documentoAdopcion = new DocumentoAdopcion();
        documentoAdopcion.setIdDocumento(1);
        documentoAdopcion.setIdSolicitud(1);
        documentoAdopcion.setTipoDocumento(" Contrato ");
        documentoAdopcion.setUrlDocumento(" https://documento.cl/1 ");
        documentoAdopcion.setFechaDocumento(LocalDate.now());
        documentoAdopcion.setEstadoDocumento(" Pendiente ");

        solicitudAdopcionDTO = new SolicitudAdopcionDTO();
        solicitudAdopcionDTO.setIdSolicitud(1);
        solicitudAdopcionDTO.setIdUsuario(1);
        solicitudAdopcionDTO.setIdMascota(1);
        solicitudAdopcionDTO.setFechaSolicitud(LocalDate.now());
        solicitudAdopcionDTO.setEstadoSolicitud("PENDIENTE");
        solicitudAdopcionDTO.setObservacionSolicitud("Solicitud válida");
    }

    @Test
    @DisplayName("Buscar documento por ID existente")
    void buscarDocumentoPorIdExistente() {
        // Given
        when(documentoAdopcionRepository.findById(1)).thenReturn(Optional.of(documentoAdopcion));

        // When
        DocumentoAdopcion resultado = documentoAdopcionService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDocumento());
        assertEquals(1, resultado.getIdSolicitud());
    }

    @Test
    @DisplayName("Buscar documento por ID inexistente")
    void buscarDocumentoPorIdInexistente() {
        // Given
        when(documentoAdopcionRepository.findById(99)).thenReturn(Optional.empty());

        // When
        DocumentoAdopcion resultado = documentoAdopcionService.buscarPorId(99);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("Guardar documento correctamente con solicitud existente")
    void guardarDocumentoCorrectamenteConSolicitudExistente() {
        // Given
        when(solicitudAdopcionClient.getSolicitudById(1)).thenReturn(solicitudAdopcionDTO);
        when(documentoAdopcionRepository.save(documentoAdopcion)).thenReturn(documentoAdopcion);

        // When
        DocumentoAdopcion resultado = documentoAdopcionService.guardarDocumento(documentoAdopcion);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDocumento());
        assertEquals(1, resultado.getIdSolicitud());
        assertEquals("CONTRATO", resultado.getTipoDocumento());
        assertEquals("https://documento.cl/1", resultado.getUrlDocumento());
        assertEquals("PENDIENTE", resultado.getEstadoDocumento());
    }

    @Test
    @DisplayName("No guardar documento si la solicitud no existe")
    void noGuardarDocumento() {
        // Given
        when(solicitudAdopcionClient.getSolicitudById(1)).thenReturn(null);

        // When
        DocumentoAdopcion resultado = documentoAdopcionService.guardarDocumento(documentoAdopcion);

        // Then
        assertNull(resultado);
    }

    @Test
    @DisplayName("No guardar documento si idSolicitud viene null")
    void noGuardarDocumentoSiIdSolicitudEsNull() {
        // Given
        documentoAdopcion.setIdSolicitud(null);

        // When
        DocumentoAdopcion resultado = documentoAdopcionService.guardarDocumento(documentoAdopcion);

        // Then
        assertNull(resultado);
    }
}

