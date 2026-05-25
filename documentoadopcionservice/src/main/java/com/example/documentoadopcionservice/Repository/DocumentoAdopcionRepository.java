package com.example.documentoadopcionservice.Repository;

import com.example.documentoadopcionservice.Model.DocumentoAdopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoAdopcionRepository extends JpaRepository<DocumentoAdopcion, Integer> {
}

