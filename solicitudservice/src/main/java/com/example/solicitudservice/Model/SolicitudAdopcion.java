package com.example.solicitudservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "solicitud_adopcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAdopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @NotNull(message = "El usuario no puede quedar vacío")
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @NotNull(message = "La mascota no puede quedar vacía")
    @Column(name = "id_mascota", nullable = false)
    private Integer idMascota;

    @NotNull(message = "La fecha de solicitud no puede quedar vacía")
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @NotBlank(message = "El estado de la solicitud no puede quedar vacío")
    @Column(name = "estado_solicitud", nullable = false, length = 50)
    private String estadoSolicitud;

    @Column(name = "observacion_solicitud", length = 255)
    private String observacionSolicitud;
}
