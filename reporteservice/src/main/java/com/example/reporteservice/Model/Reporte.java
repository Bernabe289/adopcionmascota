package com.example.reporteservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    @NotBlank(message = "El tipo de reporte no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "tipo_reporte", nullable = false, length = 50)
    private String tipoReporte;

    @NotBlank(message = "La descripción del reporte no puede quedar vacía")
    @Size(max = 255)
    @Column(name = "descripcion_reporte", nullable = false, length = 255)
    private String descripcionReporte;

    @NotNull(message = "La fecha del reporte no puede quedar vacía")
    @Column(name = "fecha_reporte", nullable = false)
    private LocalDate fechaReporte;

    @NotBlank(message = "El estado del reporte no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "estado_reporte", nullable = false, length = 50)
    private String estadoReporte;

    @NotNull(message = "El usuario no puede quedar vacío")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
}
