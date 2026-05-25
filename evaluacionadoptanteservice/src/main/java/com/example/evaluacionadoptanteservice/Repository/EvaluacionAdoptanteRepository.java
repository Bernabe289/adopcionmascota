package com.example.evaluacionadoptanteservice.Repository;

import com.example.evaluacionadoptanteservice.Model.EvaluacionAdoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionAdoptanteRepository extends JpaRepository<EvaluacionAdoptante, Integer> {
}
