package com.adopcion.adopcionmascota.Repository;

import com.adopcion.adopcionmascota.Model.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Integer> {

    boolean existsByNombreRolIgnoreCase(String nombreRol);

    Optional<RolUsuario> findByNombreRolIgnoreCase(String nombrerol);

}

