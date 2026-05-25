package com.example.documentoadopcionservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name= "documento_adopcion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoAdopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Integer idDocumento;

    @NotNull(message = "La solicitud no puede quedar vacía")
    @Column(name = "id_solicitud", nullable = false)
    private Integer idSolicitud;

    @NotBlank(message = "El tipo de documento no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "tipo_documento", nullable = false, length = 50)
    private String tipoDocumento;

    @NotBlank(message = "La URL del documento no puede quedar vacía")
    @Size(max = 255)
    @Column(name = "url_documento", nullable = false, length = 255)
    private String urlDocumento;

    @NotNull(message = "La fecha del documento no puede quedar vacía")
    @Column(name = "fecha_documento", nullable = false)
    private LocalDate fechaDocumento;

    @NotBlank(message = "El estado del documento no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "estado_documento", nullable = false, length = 50)
    private String estadoDocumento;
}
