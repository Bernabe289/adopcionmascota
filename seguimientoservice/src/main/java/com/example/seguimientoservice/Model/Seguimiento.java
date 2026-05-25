package com.example.seguimientoservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "seguimiento_postadopcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;

    @NotNull(message = "La fecha de seguimiento no puede quedar vacía")
    @Column(name = "fecha_seguimiento", nullable = false)
    private LocalDate fechaSeguimiento;

    @NotBlank(message = "La observación no puede quedar vacía")
    @Size(max = 255)
    @Column(name = "observacion_seguimiento", nullable = false, length = 255)
    private String observacionSeguimiento;

    @NotNull(message = "La solicitud no puede quedar vacía")
    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;
}