package com.example.refugioservice.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="refugio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Refugio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_refugio")
    private Integer idRefugio;

    @NotBlank(message = "El nombre no puede quedar vacío" )
    @Size(max = 150)
    @Column(name = "nombre_refugio", nullable = false, length = 150)
    private String nombreRefugio;

    @NotBlank(message = "La direccion no puede quedar vacía")
    @Size(max = 150)
    @Column(name = "direccion_refugio", nullable = false, length = 150)
    private String direccionRefugio;

    @NotBlank(message = "El telefono no puede quedar vacío")
    @Size(max = 20)
    @Column(name = "telefono_refugio", nullable = false, length = 20)
    private String telefonoRefugio;

    @NotBlank(message = "El correo no puede quedar vacio")
    @Email
    @Size( max = 100)
    @Column(name = "email_refugio", nullable = false, unique = true, length = 100)
    private String emailRefugio;

    @NotBlank(message = "El estado no puede quedar vacio")
    @Size(max = 30)
    @Column(name = "estado_refugio", nullable = false, length = 30)
    private String estadoRefugio;
}
