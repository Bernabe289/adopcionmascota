package com.example.evaluacionadoptanteservice.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudAdopcionDTO {
    private Integer idSolicitud;
    private Integer idUsuario;
    private Integer idMascota;
    private LocalDate fechaSolicitud;
    private String estadoSolicitud;
    private String observacionSolicitud;
}
