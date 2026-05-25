package com.example.vacunaservice.Repository;

import com.example.vacunaservice.Model.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Integer> {

}