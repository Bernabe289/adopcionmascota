package com.example.seguimientoservice.Repository;

import com.example.seguimientoservice.Model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Integer> {

}