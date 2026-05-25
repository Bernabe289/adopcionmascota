package com.example.notificacionservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;

    @NotBlank(message = "El mensaje no puede quedar vacío")
    @Size(max = 255)
    @Column(name = "mensaje_notificacion", nullable = false, length = 255)
    private String mensajeNotificacion;

    @NotNull(message = "La fecha no puede quedar vacía")
    @Column(name = "fecha_notificacion", nullable = false)
    private LocalDate fechaNotificacion;

    @NotBlank(message = "El estado no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "estado_notificacion", nullable = false, length = 50)
    private String estadoNotificacion;

    @NotNull(message = "El usuario no puede quedar vacío")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
}