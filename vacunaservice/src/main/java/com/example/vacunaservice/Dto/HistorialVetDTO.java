package com.example.vacunaservice.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HistorialVetDTO {

    private Integer idHistorial;
    private String descripcionHistorial;
    private LocalDate fechaRegistroHistorial;
    private Integer idMascota;
}