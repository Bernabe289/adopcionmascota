package com.example.usuarioservice.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer idUsuario;

    @NotBlank
    @Column(name="rut_usuario", nullable = false, unique = true, length = 12)
    private String rutUsuario;

    @NotBlank
    @Email
    @Column(name="email_usuario", nullable = false, unique = true ,length = 100)
    private String emailUsuario;

    @NotBlank
    @Column(name="contrasena_usuario", nullable = false, length = 100)
    private String contrasenaUsuario;

    @NotBlank
    @Column(name="pnombre_usuario", nullable = false, length = 50)
    private String pnombreUsuario;

    @NotBlank
    @Column(name="snombre_usuario", length = 50)
    private String snombreUsuario;

    @NotBlank
    @Column(name="appaterno_usuario", nullable = false, length = 50)
    private String appaternoUsuario;

    @NotBlank
    @Column(name="apmaterno_usuario", length = 50)
    private String apmaternoUsuario;

    @NotBlank
    @Column(name="telefono_usuario", nullable = false, length = 20)
    private String telefonoUsuario;

    @NotBlank
    @Column(name="direccion_usuario", nullable = false, length = 150)
    private String direccionUsuario;

    @NotBlank
    @Column(name="estado_usuario", nullable = false, length = 30)
    private String estadoUsuario;

    @Column(name="id_rol", nullable = false)
    private Integer idRol;

}
