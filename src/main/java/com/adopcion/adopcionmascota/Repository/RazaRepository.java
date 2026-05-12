package com.adopcion.adopcionmascota.Repository;

import com.adopcion.adopcionmascota.Model.Raza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RazaRepository extends JpaRepository<Raza, Integer> {
    boolean existsByNombreRazaIgnoreCase(String nombreRaza);
    Optional<Raza> findByNombreRazaIgnoreCase(String nombreRaza);
}
