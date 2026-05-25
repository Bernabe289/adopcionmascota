package com.example.solicitudservice.Repository;

import com.example.solicitudservice.Model.SolicitudAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudAdopcionRepository extends JpaRepository<SolicitudAdopcion, Integer> {

}
