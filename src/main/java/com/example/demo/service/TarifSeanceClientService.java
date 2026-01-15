package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.dto.TarifSeanceClientDTO;
import com.example.demo.entity.TarifSeanceClient;
import com.example.demo.repository.TarifSeanceClientRepository;
import com.example.demo.repository.SeanceRepository;
import com.example.demo.repository.CategorieClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarifSeanceClientService {
    @Autowired
    private TarifSeanceClientRepository tarifSeanceClientRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private CategorieClientRepository categorieClientRepository;

    public List<TarifSeanceClientDTO> getAllTarifSeanceClients() {
        return tarifSeanceClientRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TarifSeanceClientDTO> getTarifSeanceClientById(Integer id) {
        return tarifSeanceClientRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<TarifSeanceClientDTO> getBySeanceId(Integer seanceId) {
        return tarifSeanceClientRepository.findAll().stream()
                .filter(t -> t.getSeance() != null && t.getSeance().getIdSeance().equals(seanceId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TarifSeanceClientDTO createTarifSeanceClient(TarifSeanceClientDTO tarifSeanceClientDTO) {
        TarifSeanceClient tarifSeanceClient = convertToEntity(tarifSeanceClientDTO);
        TarifSeanceClient savedTarifSeanceClient = tarifSeanceClientRepository.save(tarifSeanceClient);
        return convertToDTO(savedTarifSeanceClient);
    }

    public TarifSeanceClientDTO updateTarifSeanceClient(Integer id, TarifSeanceClientDTO tarifSeanceClientDTO) {
        Optional<TarifSeanceClient> existingTarifSeanceClient = tarifSeanceClientRepository.findById(id);
        if (existingTarifSeanceClient.isPresent()) {
            TarifSeanceClient tarifSeanceClient = existingTarifSeanceClient.get();
            // Update fields if needed
            TarifSeanceClient updatedTarifSeanceClient = tarifSeanceClientRepository.save(tarifSeanceClient);
            return convertToDTO(updatedTarifSeanceClient);
        }
        return null;
    }

    public boolean deleteTarifSeanceClient(Integer id) {
        if (tarifSeanceClientRepository.existsById(id)) {
            tarifSeanceClientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TarifSeanceClientDTO convertToDTO(TarifSeanceClient tarifSeanceClient) {
        SeanceDTO seanceDTO = null;
        if (tarifSeanceClient.getSeance() != null) {
            seanceDTO = new SeanceDTO();
            seanceDTO.setIdSeance(tarifSeanceClient.getSeance().getIdSeance());
        }
        
        CategorieClientDTO categorieClientDTO = null;
        if (tarifSeanceClient.getCategorieClient() != null) {
            categorieClientDTO = new CategorieClientDTO(
                tarifSeanceClient.getCategorieClient().getIdCategorieClient(),
                tarifSeanceClient.getCategorieClient().getLibelle(),
                tarifSeanceClient.getCategorieClient().getDescription()
            );
        }
        
        return new TarifSeanceClientDTO(tarifSeanceClient.getId(),
                                      seanceDTO,
                                      categorieClientDTO,
                                      tarifSeanceClient.getTarif());
    }

    private TarifSeanceClient convertToEntity(TarifSeanceClientDTO tarifSeanceClientDTO) {
        TarifSeanceClient tarifSeanceClient = new TarifSeanceClient();
        if (tarifSeanceClientDTO.getIdSeance() != null) {
            seanceRepository.findById(tarifSeanceClientDTO.getIdSeance()).ifPresent(tarifSeanceClient::setSeance);
        }
        if (tarifSeanceClientDTO.getIdCategorieClient() != null) {
            categorieClientRepository.findById(tarifSeanceClientDTO.getIdCategorieClient()).ifPresent(tarifSeanceClient::setCategorieClient);
        }
        tarifSeanceClient.setTarif(tarifSeanceClientDTO.getTarif());
        return tarifSeanceClient;
    }
}