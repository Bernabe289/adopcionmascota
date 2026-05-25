package com.example.evaluacionadoptanteservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "evaluacion_adoptante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluacionAdoptante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Integer idEvaluacion;

    @NotNull(message = "La solicitud no puede quedar vacía")
    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;

    @NotBlank(message = "El resultado no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "resultado_evaluacion", nullable = false, length = 50)
    private String resultadoEvaluacion;

    @Size(max = 255)
    @Column(name = "observacion_evaluacion", length = 255)
    private String observacionEvaluacion;

    @NotNull(message = "La fecha de evaluación no puede quedar vacía")
    @Column(name = "fecha_evaluacion", nullable = false)
    private LocalDate fechaEvaluacion;
}
