package com.example.demo.service;

import com.example.demo.dto.DiffusionSeanceDTO;
import com.example.demo.entity.DiffusionSeance;
import com.example.demo.entity.Diffusion;
import com.example.demo.entity.Seance;
import com.example.demo.repository.DiffusionSeanceRepository;
import com.example.demo.repository.DiffusionRepository;
import com.example.demo.repository.SeanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiffusionSeanceService {

    @Autowired
    private DiffusionSeanceRepository diffusionSeanceRepository;

    @Autowired
    private DiffusionRepository diffusionRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    public List<DiffusionSeanceDTO> getAllDiffusionSeances() {
        return diffusionSeanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DiffusionSeanceDTO> getDiffusionSeancesByDiffusionId(Integer idDiffusion) {
        return diffusionSeanceRepository.findBySeanceIdSeance(idDiffusion).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DiffusionSeanceDTO> getDiffusionSeanceById(Integer id) {
        return diffusionSeanceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public DiffusionSeanceDTO createDiffusionSeance(DiffusionSeanceDTO dto) {
        DiffusionSeance entity = new DiffusionSeance();
        
        if (dto.getIdDiffusion() != null) {
            Optional<Diffusion> diffusion = diffusionRepository.findById(dto.getIdDiffusion());
            diffusion.ifPresent(entity::setDiffusion);
        }
        
        if (dto.getIdSeance() != null) {
            Optional<Seance> seance = seanceRepository.findById(dto.getIdSeance());
            seance.ifPresent(entity::setSeance);
        }
        
        entity.setDateAjout(dto.getDateAjout() != null ? dto.getDateAjout() : LocalDate.now());
        entity.setNombre(dto.getNombre() != null ? dto.getNombre() : 1);
        
        DiffusionSeance saved = diffusionSeanceRepository.save(entity);
        return convertToDTO(saved);
    }

    public boolean deleteDiffusionSeance(Integer id) {
        if (diffusionSeanceRepository.existsById(id)) {
            diffusionSeanceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DiffusionSeanceDTO> getByDiffusionId(Integer idDiffusion) {
        return diffusionSeanceRepository.findAll().stream()
                .filter(ds -> ds.getDiffusion() != null && ds.getDiffusion().getId().equals(idDiffusion))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DiffusionSeanceDTO convertToDTO(DiffusionSeance entity) {
        DiffusionSeanceDTO dto = new DiffusionSeanceDTO();
        dto.setId(entity.getId());
        dto.setDateAjout(entity.getDateAjout());
        dto.setNombre(entity.getNombre());
        
        if (entity.getDiffusion() != null) {
            dto.setIdDiffusion(entity.getDiffusion().getId());
            if (entity.getDiffusion().getPublicite() != null) {
                dto.setTitrePublicite(entity.getDiffusion().getPublicite().getTitre());
            }
            if (entity.getDiffusion().getSociete() != null) {
                dto.setNomSociete(entity.getDiffusion().getSociete().getNom());
            }
        }
        
        if (entity.getSeance() != null) {
            dto.setIdSeance(entity.getSeance().getIdSeance());
            dto.setDateSeance(entity.getSeance().getDateSeance());
            if (entity.getSeance().getSalle() != null) {
                dto.setSalleName(entity.getSeance().getSalle().getNomSalle());
            }
            if (entity.getSeance().getFilmVersion() != null && entity.getSeance().getFilmVersion().getFilm() != null) {
                dto.setFilmTitre(entity.getSeance().getFilmVersion().getFilm().getTitre());
            }
        }
        
        return dto;
    }
}
