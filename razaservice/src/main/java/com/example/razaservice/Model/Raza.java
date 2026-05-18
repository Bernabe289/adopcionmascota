package com.example.razaservice.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "raza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_raza")
    private Integer idRaza;

    @Column(name = "nombre_raza", nullable = false, unique = true, length = 50)
    private String nombreRaza;

    @ManyToOne
    @JoinColumn(name = "id_especie", nullable = false)
    private Integer idEspecie;
}


