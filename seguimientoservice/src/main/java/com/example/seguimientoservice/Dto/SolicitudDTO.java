package com.example.seguimientoservice.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudDTO {

    private Integer idSolicitud;
    private LocalDate fechaSolicitud;
    private String estadoSolicitud;
    private Integer idUsuario;
    private Integer idMascota;
}
