package com.example.solicitudservice.Dto;

import lombok.Data;

@Data
public class MascotaDTO {
    private Integer idMascota;
    private String nombreMascota;
    private Integer edadMascota;
    private String sexoMascota;
    private String tamanoMascota;
    private String estadoMascota;
    private String descripcionMascota;
    private Integer idRaza;
    private Integer idRefugio;
}
