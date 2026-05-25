package com.example.reporteservice.Service;

import com.example.reporteservice.Model.Reporte;
import com.example.reporteservice.Repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    public Reporte guardarReporte(Reporte reporte) {
        if (reporte.getIdUsuario() == null) {
            return null;
        }

        reporte.setTipoReporte(reporte.getTipoReporte().trim().toUpperCase());
        reporte.setDescripcionReporte(reporte.getDescripcionReporte().trim());
        reporte.setEstadoReporte(reporte.getEstadoReporte().trim().toUpperCase());

        return reporteRepository.save(reporte);
    }

    public Reporte buscarPorId(Integer id) {
        return reporteRepository.findById(id).orElse(null);
    }

    public Reporte actualizarReporte(Integer id, Reporte reporte) {
        Reporte reporteExistente = reporteRepository.findById(id).orElse(null);

        if (reporteExistente == null) {
            return null;
        }

        if (reporte.getIdUsuario() == null) {
            return null;
        }

        reporteExistente.setTipoReporte(reporte.getTipoReporte().trim().toUpperCase());
        reporteExistente.setDescripcionReporte(reporte.getDescripcionReporte().trim());
        reporteExistente.setFechaReporte(reporte.getFechaReporte());
        reporteExistente.setEstadoReporte(reporte.getEstadoReporte().trim().toUpperCase());
        reporteExistente.setIdUsuario(reporte.getIdUsuario());

        return reporteRepository.save(reporteExistente);
    }

    public boolean eliminarReporte(Integer id) {
        if (!reporteRepository.existsById(id)) {
            return false;
        }

        reporteRepository.deleteById(id);
        return true;
    }
}
