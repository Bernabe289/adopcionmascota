package com.example.visitaservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "visita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visita")
    private Integer idVisita;

    @NotNull(message = "La fecha de visita no puede quedar vacía")
    @Column(name = "fecha_visita", nullable = false)
    private LocalDate fechaVisita;

    @NotBlank(message = "El estado de visita no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "estado_visita", nullable = false, length = 50)
    private String estadoVisita;

    @NotNull(message = "La solicitud no puede quedar vacía")
    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;
}