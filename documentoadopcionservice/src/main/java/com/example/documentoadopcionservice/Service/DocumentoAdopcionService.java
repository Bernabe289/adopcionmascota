package com.example.documentoadopcionservice.Service;

import com.example.documentoadopcionservice.Client.SolicitudAdopcionClient;
import com.example.documentoadopcionservice.Dto.SolicitudAdopcionDTO;
import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import com.example.documentoadopcionservice.Repository.DocumentoAdopcionRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DocumentoAdopcionService {

    @Autowired
    private DocumentoAdopcionRepository documentoAdopcionRepository;

    @Autowired
    private SolicitudAdopcionClient solicitudAdopcionClient;

    public List<DocumentoAdopcion> listarDocumentos() {
        log.info("Listando documentos de adopción");
        return documentoAdopcionRepository.findAll();
    }

    public DocumentoAdopcion guardarDocumento(DocumentoAdopcion documentoAdopcion) {
        if (documentoAdopcion.getIdSolicitud() == null) {
            log.warn("No se pudo crear el documento: idSolicitud viene null");
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(documentoAdopcion.getIdSolicitud());

            if (solicitud == null) {
                log.warn("No se pudo crear el documento: solicitud ID {} no existe", documentoAdopcion.getIdSolicitud());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear el documento: solicitud ID {} no existe", documentoAdopcion.getIdSolicitud());
            return null;
        }

        documentoAdopcion.setTipoDocumento(documentoAdopcion.getTipoDocumento().trim().toUpperCase());
        documentoAdopcion.setUrlDocumento(documentoAdopcion.getUrlDocumento().trim());
        documentoAdopcion.setEstadoDocumento(documentoAdopcion.getEstadoDocumento().trim().toUpperCase());

        DocumentoAdopcion documentoGuardado = documentoAdopcionRepository.save(documentoAdopcion);

        log.info("Documento de adopción creado correctamente con ID {} para solicitud ID {}",
                documentoGuardado.getIdDocumento(),
                documentoGuardado.getIdSolicitud());

        return documentoGuardado;
    }

    public DocumentoAdopcion buscarPorId(Integer id) {
        log.info("Buscando documento de adopción con ID {}", id);
        return documentoAdopcionRepository.findById(id).orElse(null);
    }

    public DocumentoAdopcion actualizarDocumento(Integer id, DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion documentoExistente = documentoAdopcionRepository.findById(id).orElse(null);

        if (documentoExistente == null) {
            log.warn("No se pudo actualizar el documento: no existe documento con ID {}", id);
            return null;
        }

        if (documentoAdopcion.getIdSolicitud() == null) {
            log.warn("No se pudo actualizar el documento ID {}: idSolicitud viene null", id);
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(documentoAdopcion.getIdSolicitud());

            if (solicitud == null) {
                log.warn("No se pudo actualizar el documento ID {}: solicitud ID {} no existe",
                        id, documentoAdopcion.getIdSolicitud());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar el documento ID {}: solicitud ID {} no existe",
                    id, documentoAdopcion.getIdSolicitud());
            return null;
        }

        documentoExistente.setIdSolicitud(documentoAdopcion.getIdSolicitud());
        documentoExistente.setTipoDocumento(documentoAdopcion.getTipoDocumento().trim().toUpperCase());
        documentoExistente.setUrlDocumento(documentoAdopcion.getUrlDocumento().trim());
        documentoExistente.setFechaDocumento(documentoAdopcion.getFechaDocumento());
        documentoExistente.setEstadoDocumento(documentoAdopcion.getEstadoDocumento().trim().toUpperCase());

        DocumentoAdopcion documentoActualizado = documentoAdopcionRepository.save(documentoExistente);

        log.info("Documento de adopción actualizado correctamente con ID {}", documentoActualizado.getIdDocumento());

        return documentoActualizado;
    }

    public boolean eliminarDocumento(Integer id) {
        if (!documentoAdopcionRepository.existsById(id)) {
            log.warn("No se pudo eliminar el documento: no existe documento con ID {}", id);
            return false;
        }

        documentoAdopcionRepository.deleteById(id);
        log.info("Documento de adopción eliminado correctamente con ID {}", id);
        return true;
    }
}