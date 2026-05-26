package com.example.reporteservice.Dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String rutUsuario;
    private String emailUsuario;
    private String pnombreUsuario;
    private String appaternoUsuario;
    private String estadoUsuario;
    private Integer idRol;
}
