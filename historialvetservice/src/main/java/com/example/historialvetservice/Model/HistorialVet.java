package com.example.historialvetservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "historial_veterinario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialVet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @NotBlank(message = "La descripción no puede quedar vacía")
    @Size(max = 255)
    @Column(name = "descripcion_historial", nullable = false, length = 255)
    private String descripcionHistorial;

    @NotNull(message = "La fecha de registro no puede quedar vacía")
    @Column(name = "fecharegistro_historial", nullable = false)
    private LocalDate fechaRegistroHistorial;

    @NotNull(message = "La mascota no puede quedar vacía")
    @Column(name = "id_mascota", nullable = false)
    private Integer idMascota;
}