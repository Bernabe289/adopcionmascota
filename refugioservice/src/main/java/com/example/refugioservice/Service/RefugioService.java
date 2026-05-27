package com.example.refugioservice.Service;

import com.example.refugioservice.Model.Refugio;
import com.example.refugioservice.Repository.RefugioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class RefugioService {

    private static final Logger logger = LoggerFactory.getLogger(RefugioService.class);

    @Autowired
    private RefugioRepository refugioRepository;

    public List<Refugio> listarRefugios(){
        logger.info("Listando refugios");
        return refugioRepository.findAll();
    }

    public Refugio guardarRefugio(Refugio refugio){
        String emailNormalizado = refugio.getEmailRefugio().trim().toLowerCase();

        if(refugioRepository.existsByEmailRefugioIgnoreCase(emailNormalizado)){
            logger.warn("No se pudo crear el refugio: ya existe un refugio con el email {}", emailNormalizado);
            return null;
        }

        refugio.setNombreRefugio(refugio.getNombreRefugio().trim().toUpperCase());
        refugio.setDireccionRefugio(refugio.getDireccionRefugio().trim());
        refugio.setTelefonoRefugio(refugio.getTelefonoRefugio().trim());
        refugio.setEmailRefugio(emailNormalizado);
        refugio.setEstadoRefugio(refugio.getEstadoRefugio().trim().toUpperCase());

        Refugio refugioGuardado = refugioRepository.save(refugio);
        logger.info("Refugio creado correctamente con ID {}", refugioGuardado.getIdRefugio());

        return refugioGuardado;
    }

    public Refugio buscarPorId(Integer id){
        logger.info("Buscando refugio con ID {}", id);
        return refugioRepository.findById(id).orElse(null);
    }

    public Refugio actualizarRefugio(Integer id, Refugio refugio){
        Refugio refugioExistente = refugioRepository.findById(id).orElse(null);

        if (refugioExistente == null){
            logger.warn("No se pudo actualizar el refugio: no existe refugio con ID {}", id);
            return null;
        }

        String emailNormalizado = refugio.getEmailRefugio().trim().toLowerCase();

        Optional<Refugio> refugioConEmail = refugioRepository.findByEmailRefugioIgnoreCase(emailNormalizado);

        if(refugioConEmail.isPresent() && !refugioConEmail.get().getIdRefugio().equals(id)){
            logger.warn("No se pudo actualizar el refugio ID {}: ya existe otro refugio con el email {}", id, emailNormalizado);
            return null;
        }

        refugioExistente.setNombreRefugio(refugio.getNombreRefugio().trim().toUpperCase());
        refugioExistente.setDireccionRefugio(refugio.getDireccionRefugio().trim());
        refugioExistente.setTelefonoRefugio(refugio.getTelefonoRefugio().trim());
        refugioExistente.setEmailRefugio(emailNormalizado);
        refugioExistente.setEstadoRefugio(refugio.getEstadoRefugio().trim().toUpperCase());

        Refugio refugioActualizado = refugioRepository.save(refugioExistente);
        logger.info("Refugio ID {} actualizado correctamente", refugioActualizado.getIdRefugio());

        return refugioActualizado;
    }

    public boolean eliminarRefugio(Integer id){
        if (!refugioRepository.existsById(id)){
            logger.warn("No se pudo eliminar el refugio: no existe refugio con ID {}", id);
            return false;
        }

        refugioRepository.deleteById(id);
        logger.info("Refugio ID {} eliminado correctamente", id);

        return true;
    }
}
