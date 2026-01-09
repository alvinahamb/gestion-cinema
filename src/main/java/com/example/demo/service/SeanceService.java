package com.example.demo.service;

import com.example.demo.dto.SeanceDTO;
import com.example.demo.entity.Seance;
import com.example.demo.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;

    public List<SeanceDTO> getAllSeances() {
        return seanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SeanceDTO> getSeanceById(Integer id) {
        return seanceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SeanceDTO createSeance(SeanceDTO seanceDTO) {
        Seance seance = convertToEntity(seanceDTO);
        Seance savedSeance = seanceRepository.save(seance);
        return convertToDTO(savedSeance);
    }

    public SeanceDTO updateSeance(Integer id, SeanceDTO seanceDTO) {
        Optional<Seance> existingSeance = seanceRepository.findById(id);
        if (existingSeance.isPresent()) {
            Seance seance = existingSeance.get();
            seance.setHeureDebut(LocalDateTime.parse(seanceDTO.getHeureDebut()));
            seance.setHeureFin(LocalDateTime.parse(seanceDTO.getHeureFin()));
            seance.setAvecSousTitre(seanceDTO.getAvecSousTitre());
            seance.setDateSeance(seanceDTO.getDateSeance());
            seance.setTarif(seanceDTO.getTarif());
            
            Seance updatedSeance = seanceRepository.save(seance);
            return convertToDTO(updatedSeance);
        }
        return null;
    }

    public boolean deleteSeance(Integer id) {
        if (seanceRepository.existsById(id)) {
            seanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SeanceDTO convertToDTO(Seance seance) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new SeanceDTO(seance.getIdSeance(), 
                            seance.getFilmVersion() != null ? seance.getFilmVersion().getId() : null,
                            seance.getSalle() != null ? seance.getSalle().getIdSalle() : null,
                            seance.getHeureDebut() != null ? seance.getHeureDebut().format(formatter) : null,
                            seance.getHeureFin() != null ? seance.getHeureFin().format(formatter) : null,
                            seance.getAvecSousTitre(),
                            seance.getDateSeance(),
                            seance.getTarif(),
                            seance.getStatusSeance() != null ? seance.getStatusSeance().getIdStatus() : null);
    }

    private Seance convertToEntity(SeanceDTO seanceDTO) {
        Seance seance = new Seance();
        if (seanceDTO.getHeureDebut() != null) {
            seance.setHeureDebut(LocalDateTime.parse(seanceDTO.getHeureDebut()));
        }
        if (seanceDTO.getHeureFin() != null) {
            seance.setHeureFin(LocalDateTime.parse(seanceDTO.getHeureFin()));
        }
        seance.setAvecSousTitre(seanceDTO.getAvecSousTitre());
        seance.setDateSeance(seanceDTO.getDateSeance());
        seance.setTarif(seanceDTO.getTarif());
        return seance;
    }
}
