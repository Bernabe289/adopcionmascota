package com.example.refugioservice.Service;

import com.example.refugioservice.Model.Refugio;
import com.example.refugioservice.Repository.RefugioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefugioService {

    @Autowired
    private RefugioRepository refugioRepository;

    public List<Refugio> listarRefugios(){
        return refugioRepository.findAll();
    }

    public Refugio guardarRefugio(Refugio refugio){
        String emailNormalizado = refugio.getEmailRefugio().trim().toLowerCase();

        if(refugioRepository.existsByEmailRefugioIgnoreCase(emailNormalizado)){
            return null;
        }

        refugio.setNombreRefugio(refugio.getNombreRefugio().trim().toUpperCase());
        refugio.setDireccionRefugio(refugio.getDireccionRefugio().trim());
        refugio.setTelefonoRefugio(refugio.getTelefonoRefugio().trim());
        refugio.setEmailRefugio(emailNormalizado);
        refugio.setEstadoRefugio(refugio.getEstadoRefugio().trim().toUpperCase());

        return refugioRepository.save(refugio);
    }

    public Refugio buscarPorId(Integer id){
        return refugioRepository.findById(id).orElse(null);
    }

    public Refugio actualizarRefugio(Integer id, Refugio refugio){
        Refugio refugioExistente = refugioRepository.findById(id).orElse(null);

        if (refugioExistente == null){
            return null;
        }

        String emailNormalizado = refugio.getEmailRefugio().trim().toLowerCase();

        Optional<Refugio> refugioConEmail = refugioRepository.findByEmailRefugioIgnoreCase(emailNormalizado);

        if(refugioConEmail.isPresent() && !refugioConEmail.get().getIdRefugio().equals(id)){
            return null;
        }

        refugioExistente.setNombreRefugio(refugio.getNombreRefugio().trim().toUpperCase());
        refugioExistente.setDireccionRefugio(refugio.getDireccionRefugio().trim());
        refugioExistente.setTelefonoRefugio(refugio.getTelefonoRefugio().trim());
        refugioExistente.setEmailRefugio(emailNormalizado);
        refugioExistente.setEstadoRefugio(refugio.getEstadoRefugio().trim().toUpperCase());

        return refugioRepository.save(refugioExistente);
    }

    public boolean eliminarRefugio(Integer id){
        if (!refugioRepository.existsById(id)){
            return false;
        }
        refugioRepository.existsById(id);
            return true;

    }
}
