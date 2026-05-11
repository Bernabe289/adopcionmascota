package com.adopcion.adopcionmascota.Repository;

import com.adopcion.adopcionmascota.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByRutUsuarioIgnoreCase(String rutUsuario);
    boolean existsByEmailUsuarioIgnoreCase(String emailUsuario);

    Optional<Usuario> findByRutUsuarioIgnoreCase(String rutUsuario);
    Optional<Usuario> findByEmailUsuarioIgnoreCase(String emailUsuario);


}
