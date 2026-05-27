package com.example.especieservice.Service;

import com.example.especieservice.Model.Especie;
import com.example.especieservice.Repository.EspecieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    private static final Logger logger = LoggerFactory.getLogger(EspecieService.class);

    @Autowired
    private EspecieRepository especieRepository;

    public List<Especie> listarEspecies(){
        logger.info("Listando especies");
        return especieRepository.findAll();
    }

    // Valida que no exista una especie duplicada antes de guardar
    public Especie guardarEspecie(Especie especie){
        String nombre = especie.getNombreEspecie().trim().toUpperCase();

        if(especieRepository.existsByNombreEspecieIgnoreCase(nombre)){
            logger.warn("No se pudo crear la especie: ya existe una especie con el nombre {}", nombre);
            return null;
        }

        especie.setNombreEspecie(nombre);

        Especie especieGuardada = especieRepository.save(especie);
        logger.info("Especie creada correctamente con ID {}", especieGuardada.getIdEspecie());

        return especieGuardada;
    }

    public Especie buscarPorId(Integer id){
        logger.info("Buscando especie con ID {}", id);
        return especieRepository.findById(id).orElse(null);
    }

    public Especie actualizarEspecie(Integer id, Especie especie){
        Especie especieExistente = especieRepository.findById(id).orElse(null);

        if(especieExistente == null){
            logger.warn("No se pudo actualizar la especie: no existe especie con ID {}", id);
            return null;
        }

        String nombre = especie.getNombreEspecie().trim().toUpperCase();

        Optional<Especie> especieConMismoNombre = especieRepository.findByNombreEspecieIgnoreCase(nombre);

        if(especieConMismoNombre.isPresent() && !especieConMismoNombre.get().getIdEspecie().equals(id)){
            logger.warn("No se pudo actualizar la especie ID {}: ya existe otra especie con el nombre {}", id, nombre);
            return null;
        }

        especieExistente.setNombreEspecie(nombre);

        Especie especieActualizada = especieRepository.save(especieExistente);
        logger.info("Especie ID {} actualizada correctamente", especieActualizada.getIdEspecie());

        return especieActualizada;
    }

    public boolean eliminarEspecie(Integer id){
        if(!especieRepository.existsById(id)){
            logger.warn("No se pudo eliminar la especie: no existe especie con ID {}", id);
            return false;
        }

        especieRepository.deleteById(id);
        logger.info("Especie ID {} eliminada correctamente", id);

        return true;
    }
}