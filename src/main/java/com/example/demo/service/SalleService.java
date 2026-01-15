package com.example.demo.service;

import com.example.demo.dto.SalleDTO;
import com.example.demo.entity.Salle;
import com.example.demo.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalleService {
    @Autowired
    private SalleRepository salleRepository;

    public List<SalleDTO> getAllSalles() {
        return salleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SalleDTO> getSalleById(Integer id) {
        return salleRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SalleDTO createSalle(SalleDTO salleDTO) {
        Salle salle = convertToEntity(salleDTO);
        Salle savedSalle = salleRepository.save(salle);
        return convertToDTO(savedSalle);
    }

    public SalleDTO updateSalle(Integer id, SalleDTO salleDTO) {
        Optional<Salle> existingSalle = salleRepository.findById(id);
        if (existingSalle.isPresent()) {
            Salle salle = existingSalle.get();
            salle.setNomSalle(salleDTO.getNomSalle());
            salle.setCapacite(salleDTO.getCapacite());
            salle.setLigne(salleDTO.getLigne());
            salle.setColonne(salleDTO.getColonne());
            Salle updatedSalle = salleRepository.save(salle);
            return convertToDTO(updatedSalle);
        }
        return null;
    }

    public boolean deleteSalle(Integer id) {
        if (salleRepository.existsById(id)) {
            salleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SalleDTO convertToDTO(Salle salle) {
        return new SalleDTO(salle.getIdSalle(), salle.getNomSalle(), salle.getCapacite(), 
                           salle.getLigne(), salle.getColonne());
    }

    private Salle convertToEntity(SalleDTO salleDTO) {
        return new Salle(salleDTO.getNomSalle(), salleDTO.getCapacite(), 
                        salleDTO.getLigne(), salleDTO.getColonne());
    }
}
