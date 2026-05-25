package com.example.vacunaservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vacuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vacuna")
    private Integer idVacuna;

    @NotBlank(message = "El nombre de la vacuna no puede quedar vacío")
    @Size(max = 100)
    @Column(name = "nombre_vacuna", nullable = false, length = 100)
    private String nombreVacuna;

    @NotNull(message = "La fecha de la vacuna no puede quedar vacía")
    @Column(name = "fecha_vacuna", nullable = false)
    private LocalDate fechaVacuna;

    @NotNull(message = "El historial veterinario no puede quedar vacío")
    @Column(name = "id_historial", nullable = false)
    private Integer idHistorial;
}