package com.example.mascotaservice.Repository;

import com.example.mascotaservice.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {

}
//No va optional porque no tiene que validar duplicados