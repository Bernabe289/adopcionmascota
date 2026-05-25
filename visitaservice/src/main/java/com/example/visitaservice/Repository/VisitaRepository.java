package com.example.visitaservice.Repository;

import com.example.visitaservice.Model.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Integer> {

}