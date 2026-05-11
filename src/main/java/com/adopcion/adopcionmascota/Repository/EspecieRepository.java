package com.adopcion.adopcionmascota.Repository;


import com.adopcion.adopcionmascota.Model.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepository extends JpaRepository<Especie, Integer> {

    boolean existsByNombreEspecieIgnoreCase(String nombreEspecie);

}
