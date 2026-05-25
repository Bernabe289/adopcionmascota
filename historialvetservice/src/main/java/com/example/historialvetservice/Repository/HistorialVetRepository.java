package com.example.historialvetservice.Repository;

import com.example.historialvetservice.Model.HistorialVet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialVetRepository extends JpaRepository<HistorialVet, Integer> {

}