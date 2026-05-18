package com.example.refugioservice.Repository;

import com.example.refugioservice.Model.Refugio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefugioRepository extends JpaRepository<Refugio, Integer> {

    boolean existsByEmailRefugioIgnoreCase(String emailRefugio);

    Optional<Refugio> findByEmailRefugioIgnoreCase(String emailRefugio);
}
