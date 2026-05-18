package com.example.especieservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especie")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Especie {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_especie")
private Integer idEspecie;

@Column(name = "nombre_especie", nullable = false, unique = true, length = 50)
private String nombreEspecie;
}
