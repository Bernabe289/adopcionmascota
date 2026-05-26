package com.example.documentoadopcionservice.Service;

import com.example.documentoadopcionservice.Client.SolicitudAdopcionClient;
import com.example.documentoadopcionservice.Dto.SolicitudAdopcionDTO;
import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import com.example.documentoadopcionservice.Repository.DocumentoAdopcionRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentoAdopcionService {

    @Autowired
    private DocumentoAdopcionRepository documentoAdopcionRepository;

    @Autowired
    private SolicitudAdopcionClient solicitudAdopcionClient;

    public List<DocumentoAdopcion> listarDocumentos() {
        return documentoAdopcionRepository.findAll();
    }

    public DocumentoAdopcion guardarDocumento(DocumentoAdopcion documentoAdopcion) {
        if (documentoAdopcion.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(documentoAdopcion.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }
        documentoAdopcion.setTipoDocumento(documentoAdopcion.getTipoDocumento().trim().toUpperCase());
        documentoAdopcion.setUrlDocumento(documentoAdopcion.getUrlDocumento().trim());
        documentoAdopcion.setEstadoDocumento(documentoAdopcion.getEstadoDocumento().trim().toUpperCase());

        return documentoAdopcionRepository.save(documentoAdopcion);
    }

    public DocumentoAdopcion buscarPorId(Integer id) {
        return documentoAdopcionRepository.findById(id).orElse(null);
    }

    public DocumentoAdopcion actualizarDocumento(Integer id, DocumentoAdopcion documentoAdopcion) {
        DocumentoAdopcion documentoExistente = documentoAdopcionRepository.findById(id).orElse(null);

        if (documentoExistente == null) {
            return null;
        }

        if (documentoAdopcion.getIdSolicitud() == null) {
            return null;
        }

        try {
            SolicitudAdopcionDTO solicitud = solicitudAdopcionClient.getSolicitudById(documentoAdopcion.getIdSolicitud());

            if (solicitud == null) {
                return null;
            }

        } catch (FeignException error) {
            return null;
        }

        documentoExistente.setIdSolicitud(documentoAdopcion.getIdSolicitud());
        documentoExistente.setTipoDocumento(documentoAdopcion.getTipoDocumento().trim().toUpperCase());
        documentoExistente.setUrlDocumento(documentoAdopcion.getUrlDocumento().trim());
        documentoExistente.setFechaDocumento(documentoAdopcion.getFechaDocumento());
        documentoExistente.setEstadoDocumento(documentoAdopcion.getEstadoDocumento().trim().toUpperCase());

        return documentoAdopcionRepository.save(documentoExistente);
    }

    public boolean eliminarDocumento(Integer id) {
        if (!documentoAdopcionRepository.existsById(id)) {
            return false;
        }

        documentoAdopcionRepository.deleteById(id);
        return true;
    }
}
