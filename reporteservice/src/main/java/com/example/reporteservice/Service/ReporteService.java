package com.example.reporteservice.Service;

import com.example.reporteservice.Client.UsuarioClient;
import com.example.reporteservice.Dto.UsuarioDTO;
import com.example.reporteservice.Model.Reporte;
import com.example.reporteservice.Repository.ReporteRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Reporte> listarReportes() {
        log.info("Listando reportes");
        return reporteRepository.findAll();
    }

    public Reporte guardarReporte(Reporte reporte) {
        if (reporte.getIdUsuario() == null) {
            log.warn("No se pudo crear el reporte: idUsuario viene null");
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(reporte.getIdUsuario());

            if (usuario == null) {
                log.warn("No se pudo crear el reporte: usuario ID {} no existe", reporte.getIdUsuario());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo crear el reporte: usuario ID {} no existe", reporte.getIdUsuario());
            return null;
        }

        reporte.setTipoReporte(reporte.getTipoReporte().trim().toUpperCase());
        reporte.setDescripcionReporte(reporte.getDescripcionReporte().trim());
        reporte.setEstadoReporte(reporte.getEstadoReporte().trim().toUpperCase());

        Reporte reporteGuardado = reporteRepository.save(reporte);

        log.info("Reporte creado correctamente con ID {} para usuario ID {}",
                reporteGuardado.getIdReporte(),
                reporteGuardado.getIdUsuario());

        return reporteGuardado;
    }

    public Reporte buscarPorId(Integer id) {
        log.info("Buscando reporte con ID {}", id);
        return reporteRepository.findById(id).orElse(null);
    }

    public Reporte actualizarReporte(Integer id, Reporte reporte) {
        Reporte reporteExistente = reporteRepository.findById(id).orElse(null);

        if (reporteExistente == null) {
            log.warn("No se pudo actualizar el reporte: no existe reporte con ID {}", id);
            return null;
        }

        if (reporte.getIdUsuario() == null) {
            log.warn("No se pudo actualizar el reporte ID {}: idUsuario viene null", id);
            return null;
        }

        try {
            UsuarioDTO usuario = usuarioClient.getUsuarioById(reporte.getIdUsuario());

            if (usuario == null) {
                log.warn("No se pudo actualizar el reporte ID {}: usuario ID {} no existe",
                        id, reporte.getIdUsuario());
                return null;
            }

        } catch (FeignException error) {
            log.warn("No se pudo actualizar el reporte ID {}: usuario ID {} no existe",
                    id, reporte.getIdUsuario());
            return null;
        }

        reporteExistente.setTipoReporte(reporte.getTipoReporte().trim().toUpperCase());
        reporteExistente.setDescripcionReporte(reporte.getDescripcionReporte().trim());
        reporteExistente.setFechaReporte(reporte.getFechaReporte());
        reporteExistente.setEstadoReporte(reporte.getEstadoReporte().trim().toUpperCase());
        reporteExistente.setIdUsuario(reporte.getIdUsuario());

        Reporte reporteActualizado = reporteRepository.save(reporteExistente);

        log.info("Reporte actualizado correctamente con ID {}", reporteActualizado.getIdReporte());

        return reporteActualizado;
    }

    public boolean eliminarReporte(Integer id) {
        if (!reporteRepository.existsById(id)) {
            log.warn("No se pudo eliminar el reporte: no existe reporte con ID {}", id);
            return false;
        }

        reporteRepository.deleteById(id);
        log.info("Reporte eliminado correctamente con ID {}", id);
        return true;
    }
}