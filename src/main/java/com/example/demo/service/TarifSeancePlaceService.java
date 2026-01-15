package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.dto.TarifSeancePlaceDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.entity.CategorieClient;
import com.example.demo.entity.Seance;
import com.example.demo.entity.TarifSeancePlace;
import com.example.demo.entity.TypePlace;
import com.example.demo.repository.CategorieClientRepository;
import com.example.demo.repository.SeanceRepository;
import com.example.demo.repository.TarifSeancePlaceRepository;
import com.example.demo.repository.TypePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarifSeancePlaceService {
    @Autowired
    private TarifSeancePlaceRepository tarifSeancePlaceRepository;
    
    @Autowired
    private SeanceRepository seanceRepository;
    
    @Autowired
    private CategorieClientRepository categorieClientRepository;
    
    @Autowired
    private TypePlaceRepository typePlaceRepository;

    public List<TarifSeancePlaceDTO> getAllTarifSeancePlaces() {
        return tarifSeancePlaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TarifSeancePlaceDTO> getTarifSeancePlaceById(Integer id) {
        return tarifSeancePlaceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<TarifSeancePlaceDTO> getBySeanceId(Integer seanceId) {
        return tarifSeancePlaceRepository.findAll().stream()
                .filter(tsp -> tsp.getSeance() != null && tsp.getSeance().getIdSeance().equals(seanceId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TarifSeancePlaceDTO createTarifSeancePlace(TarifSeancePlaceDTO dto) {
        TarifSeancePlace entity = convertToEntity(dto);
        TarifSeancePlace saved = tarifSeancePlaceRepository.save(entity);
        return convertToDTO(saved);
    }

    public TarifSeancePlaceDTO updateTarifSeancePlace(Integer id, TarifSeancePlaceDTO dto) {
        Optional<TarifSeancePlace> existing = tarifSeancePlaceRepository.findById(id);
        if (existing.isPresent()) {
            TarifSeancePlace entity = existing.get();
            
            if (dto.getIdSeance() != null) {
                seanceRepository.findById(dto.getIdSeance())
                    .ifPresent(entity::setSeance);
            }
            
            if (dto.getIdCategorieClient() != null) {
                categorieClientRepository.findById(dto.getIdCategorieClient())
                    .ifPresent(entity::setCategorieClient);
            }
            
            if (dto.getIdTypePlace() != null) {
                typePlaceRepository.findById(dto.getIdTypePlace())
                    .ifPresent(entity::setTypePlace);
            }
            
            entity.setTarif(dto.getTarif());
            TarifSeancePlace updated = tarifSeancePlaceRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deleteTarifSeancePlace(Integer id) {
        if (tarifSeancePlaceRepository.existsById(id)) {
            tarifSeancePlaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TarifSeancePlaceDTO convertToDTO(TarifSeancePlace entity) {
        SeanceDTO seanceDTO = null;
        if (entity.getSeance() != null) {
            seanceDTO = new SeanceDTO();
            seanceDTO.setIdSeance(entity.getSeance().getIdSeance());
        }
        
        CategorieClientDTO categorieClientDTO = null;
        if (entity.getCategorieClient() != null) {
            CategorieClient cc = entity.getCategorieClient();
            categorieClientDTO = new CategorieClientDTO(cc.getIdCategorieClient(), 
                                                        cc.getLibelle(), cc.getDescription());
        }
        
        TypePlaceDTO typePlaceDTO = null;
        if (entity.getTypePlace() != null) {
            TypePlace tp = entity.getTypePlace();
            typePlaceDTO = new TypePlaceDTO(tp.getIdTypePlace(), tp.getLibelle(), tp.getDescription());
        }
        
        return new TarifSeancePlaceDTO(
            entity.getId(),
            seanceDTO,
            categorieClientDTO,
            typePlaceDTO,
            entity.getTarif()
        );
    }

    private TarifSeancePlace convertToEntity(TarifSeancePlaceDTO dto) {
        Seance seance = null;
        if (dto.getIdSeance() != null) {
            seance = seanceRepository.findById(dto.getIdSeance()).orElse(null);
        }
        
        CategorieClient categorieClient = null;
        if (dto.getIdCategorieClient() != null) {
            categorieClient = categorieClientRepository.findById(dto.getIdCategorieClient()).orElse(null);
        }
        
        TypePlace typePlace = null;
        if (dto.getIdTypePlace() != null) {
            typePlace = typePlaceRepository.findById(dto.getIdTypePlace()).orElse(null);
        }
        
        return new TarifSeancePlace(seance, categorieClient, typePlace, dto.getTarif());
    }
}
