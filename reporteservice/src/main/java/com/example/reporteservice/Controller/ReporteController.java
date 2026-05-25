package com.example.reporteservice.Controller;

import com.example.reporteservice.Model.Reporte;
import com.example.reporteservice.Service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> getReportes() {
        List<Reporte> reportes = reporteService.listarReportes();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    @PostMapping
    public ResponseEntity<?> createReporte(@Valid @RequestBody Reporte reporte) {
        Reporte nuevoReporte = reporteService.guardarReporte(reporte);

        if (nuevoReporte == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el reporte. Verifique que los datos sean correctos.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Integer id) {
        Reporte reporte = reporteService.buscarPorId(id);

        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(reporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReporte(@PathVariable Integer id, @Valid @RequestBody Reporte reporte) {
        Reporte reporteActualizado = reporteService.actualizarReporte(id, reporte);

        if (reporteActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el reporte. Verifique que exista y que los datos sean correctos.");
        }

        return ResponseEntity.ok(reporteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReporte(@PathVariable Integer id) {
        boolean eliminado = reporteService.eliminarReporte(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte no encontrado.");
        }

        return ResponseEntity.ok("Reporte eliminado correctamente.");
    }
}
