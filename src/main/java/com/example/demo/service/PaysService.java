package com.example.demo.service;

import com.example.demo.dto.PaysDTO;
import com.example.demo.entity.Pays;
import com.example.demo.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaysService {
    @Autowired
    private PaysRepository paysRepository;

    public List<PaysDTO> getAllPays() {
        return paysRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PaysDTO> getPaysById(Integer id) {
        return paysRepository.findById(id)
                .map(this::convertToDTO);
    }

    public PaysDTO createPays(PaysDTO paysDTO) {
        Pays pays = convertToEntity(paysDTO);
        Pays savedPays = paysRepository.save(pays);
        return convertToDTO(savedPays);
    }

    public PaysDTO updatePays(Integer id, PaysDTO paysDTO) {
        Optional<Pays> existingPays = paysRepository.findById(id);
        if (existingPays.isPresent()) {
            Pays pays = existingPays.get();
            pays.setNom(paysDTO.getNom());
            Pays updatedPays = paysRepository.save(pays);
            return convertToDTO(updatedPays);
        }
        return null;
    }

    public boolean deletePays(Integer id) {
        if (paysRepository.existsById(id)) {
            paysRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PaysDTO convertToDTO(Pays pays) {
        return new PaysDTO(pays.getIdPays(), pays.getNom());
    }

    private Pays convertToEntity(PaysDTO paysDTO) {
        return new Pays(paysDTO.getNom());
    }
}
