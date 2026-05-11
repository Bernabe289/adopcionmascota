package com.adopcion.adopcionmascota.Repository;

import com.adopcion.adopcionmascota.Model.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Integer> {

    boolean existsByNombreRolIgnoreCase(String nombreRol);

}

