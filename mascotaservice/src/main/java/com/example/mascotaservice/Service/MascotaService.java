package com.example.mascotaservice.Service;

import com.example.mascotaservice.Model.Mascota;
import com.example.mascotaservice.Repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    public Mascota guardarMascota(Mascota mascota) {

        // Valida que la mascota tenga raza y refugio asignados antes de guardar
        if (mascota.getIdRaza() == null || mascota.getIdRefugio() == null) {
            return null;
        }

        mascota.setNombreMascota(mascota.getNombreMascota().trim().toUpperCase());
        mascota.setSexoMascota(mascota.getSexoMascota().trim().toUpperCase());
        mascota.setTamanoMascota(mascota.getTamanoMascota().trim().toUpperCase());
        mascota.setEstadoMascota(mascota.getEstadoMascota().trim().toUpperCase());

        return mascotaRepository.save(mascota);
    }

    public Mascota buscarPorId(Integer id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    public Mascota actualizarMascota(Integer id, Mascota mascota) {
        Mascota mascotaExistente = mascotaRepository.findById(id).orElse(null);

        if (mascotaExistente == null) {
            return null;
        }

        // Actualiza la mascota manteniendo solo los IDs de raza y refugio
        if (mascota.getIdRaza() == null || mascota.getIdRefugio() == null) {
            return null;
        }

        mascotaExistente.setNombreMascota(mascota.getNombreMascota().trim().toUpperCase());
        mascotaExistente.setEdadMascota(mascota.getEdadMascota());
        mascotaExistente.setSexoMascota(mascota.getSexoMascota().trim().toUpperCase());
        mascotaExistente.setTamanoMascota(mascota.getTamanoMascota().trim().toUpperCase());
        mascotaExistente.setEstadoMascota(mascota.getEstadoMascota().trim().toUpperCase());
        mascotaExistente.setDescripcionMascota(mascota.getDescripcionMascota());
        mascotaExistente.setIdRaza(mascota.getIdRaza());
        mascotaExistente.setIdRefugio(mascota.getIdRefugio());

        return mascotaRepository.save(mascotaExistente);
    }

    public boolean eliminarMascota(Integer id) {
        if (!mascotaRepository.existsById(id)) {
            return false;
        }

        mascotaRepository.deleteById(id);
        return true;
    }
}