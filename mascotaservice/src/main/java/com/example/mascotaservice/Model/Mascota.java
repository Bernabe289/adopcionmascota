package com.example.mascotaservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mascota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Integer idMascota;

    @NotBlank(message = "El nombre no puede quedar vacío")
    @Size(max = 100)
    @Column(name = "nombre_mascota", nullable = false, length = 100)
    private String nombreMascota;

    @NotNull(message = "La edad no puede quedar vacía")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Column(name = "edad_mascota", nullable = false)
    private Integer edadMascota;

    @NotBlank(message = "El sexo no puede quedar vacío")
    @Size(max = 20)
    @Column(name = "sexo_mascota", nullable = false, length = 20)
    private String sexoMascota;

    @NotBlank(message = "El tamaño no puede quedar vacío")
    @Size(max = 50)
    @Column(name = "tamano_mascota", nullable = false, length = 50)
    private String tamanoMascota;

    @NotBlank
    @Size(max = 50)
    @Column(name = "estado_mascota", nullable = false, length = 50)
    private String estadoMascota;

    @Size(max = 255)
    @Column(name = "descripcion_mascota", length = 255)
    private String descripcionMascota;

    @NotNull(message = "La raza no puede quedar vacía")
    @Column(name = "id_raza", nullable = false)
    private Integer idRaza;

    @NotNull(message = "El refugio no puede quedar vacío")
    @Column(name = "id_refugio", nullable = false)
    private Integer idRefugio;

}
