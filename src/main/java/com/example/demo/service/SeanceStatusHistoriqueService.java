package com.example.demo.service;

import com.example.demo.dto.SeanceStatusHistoriqueDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.dto.StatusDTO;
import com.example.demo.entity.Seance;
import com.example.demo.entity.SeanceStatusHistorique;
import com.example.demo.entity.Status;
import com.example.demo.repository.SeanceRepository;
import com.example.demo.repository.SeanceStatusHistoriqueRepository;
import com.example.demo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeanceStatusHistoriqueService {
    @Autowired
    private SeanceStatusHistoriqueRepository seanceStatusHistoriqueRepository;
    
    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private StatusRepository statusRepository;

    public List<SeanceStatusHistoriqueDTO> getAllSeanceStatusHistoriques() {
        return seanceStatusHistoriqueRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SeanceStatusHistoriqueDTO> getSeanceStatusHistoriqueById(Integer id) {
        return seanceStatusHistoriqueRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<SeanceStatusHistoriqueDTO> getBySeanceId(Integer seanceId) {
        return seanceStatusHistoriqueRepository.findAll().stream()
                .filter(ssh -> ssh.getSeance() != null && ssh.getSeance().getIdSeance().equals(seanceId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SeanceStatusHistoriqueDTO createSeanceStatusHistorique(SeanceStatusHistoriqueDTO dto) {
        SeanceStatusHistorique entity = convertToEntity(dto);
        SeanceStatusHistorique saved = seanceStatusHistoriqueRepository.save(entity);
        return convertToDTO(saved);
    }

    public SeanceStatusHistoriqueDTO updateSeanceStatusHistorique(Integer id, SeanceStatusHistoriqueDTO dto) {
        Optional<SeanceStatusHistorique> existing = seanceStatusHistoriqueRepository.findById(id);
        if (existing.isPresent()) {
            SeanceStatusHistorique entity = existing.get();
            
            if (dto.getIdSeance() != null) {
                seanceRepository.findById(dto.getIdSeance())
                    .ifPresent(entity::setSeance);
            }
            
            if (dto.getIdStatus() != null) {
                statusRepository.findById(dto.getIdStatus())
                    .ifPresent(entity::setStatus);
            }
            
            entity.setDateModification(dto.getDateModification());
            SeanceStatusHistorique updated = seanceStatusHistoriqueRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deleteSeanceStatusHistorique(Integer id) {
        if (seanceStatusHistoriqueRepository.existsById(id)) {
            seanceStatusHistoriqueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private SeanceStatusHistoriqueDTO convertToDTO(SeanceStatusHistorique entity) {
        SeanceDTO seanceDTO = null;
        if (entity.getSeance() != null) {
            seanceDTO = new SeanceDTO();
            seanceDTO.setIdSeance(entity.getSeance().getIdSeance());
        }
        
        StatusDTO statusDTO = null;
        if (entity.getStatus() != null) {
            Status status = entity.getStatus();
            statusDTO = new StatusDTO(status.getIdStatus(), status.getLibelle(), 
                                      status.getCode(), status.getOrdre(), null);
        }
        
        return new SeanceStatusHistoriqueDTO(
            entity.getId(),
            seanceDTO,
            statusDTO,
            entity.getDateModification()
        );
    }

    private SeanceStatusHistorique convertToEntity(SeanceStatusHistoriqueDTO dto) {
        Seance seance = null;
        if (dto.getIdSeance() != null) {
            seance = seanceRepository.findById(dto.getIdSeance()).orElse(null);
        }
        
        Status status = null;
        if (dto.getIdStatus() != null) {
            status = statusRepository.findById(dto.getIdStatus()).orElse(null);
        }
        
        return new SeanceStatusHistorique(seance, status, dto.getDateModification());
    }
}
