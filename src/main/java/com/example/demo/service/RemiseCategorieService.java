package com.example.demo.service;

import com.example.demo.dto.RemiseCategorieDTO;
import com.example.demo.entity.RemiseCategorie;
import com.example.demo.entity.TypePlace;
import com.example.demo.entity.CategorieClient;
import com.example.demo.repository.RemiseCategorieRepository;
import com.example.demo.repository.TypePlaceRepository;
import com.example.demo.repository.CategorieClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RemiseCategorieService {
    @Autowired
    private RemiseCategorieRepository remiseCategorieRepository;

    @Autowired
    private TypePlaceRepository typePlaceRepository;

    @Autowired
    private CategorieClientRepository categorieCategoryRepository;

    public List<RemiseCategorieDTO> getAllRemiseCategories() {
        return remiseCategorieRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RemiseCategorieDTO> getRemiseCategorieById(Integer id) {
        return remiseCategorieRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<RemiseCategorieDTO> getRemisesByTypePlace(Integer idTypePlace) {
        return remiseCategorieRepository.findByTypePlaceIdTypePlace(idTypePlace).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RemiseCategorieDTO> getRemisesByCategorie(Integer idCategorieClient) {
        return remiseCategorieRepository.findByCategorieClientIdCategorieClient(idCategorieClient).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RemiseCategorieDTO createRemiseCategorie(RemiseCategorieDTO remiseCategorieDTO) {
        RemiseCategorie remiseCategorie = convertToEntity(remiseCategorieDTO);
        RemiseCategorie savedRemiseCategorie = remiseCategorieRepository.save(remiseCategorie);
        return convertToDTO(savedRemiseCategorie);
    }

    public RemiseCategorieDTO updateRemiseCategorie(Integer id, RemiseCategorieDTO remiseCategorieDTO) {
        Optional<RemiseCategorie> existingRemiseCategorie = remiseCategorieRepository.findById(id);
        if (existingRemiseCategorie.isPresent()) {
            RemiseCategorie remiseCategorie = existingRemiseCategorie.get();
            remiseCategorie.setTarif(remiseCategorieDTO.getTarif());
            remiseCategorie.setPourcentageDeReduction(remiseCategorieDTO.getPourcentageDeReduction());
            RemiseCategorie updatedRemiseCategorie = remiseCategorieRepository.save(remiseCategorie);
            return convertToDTO(updatedRemiseCategorie);
        }
        return null;
    }

    public void deleteRemiseCategorie(Integer id) {
        remiseCategorieRepository.deleteById(id);
    }

    private RemiseCategorieDTO convertToDTO(RemiseCategorie remiseCategorie) {
        return new RemiseCategorieDTO(
                remiseCategorie.getIdRemise(),
                remiseCategorie.getTypePlace().getIdTypePlace(),
                remiseCategorie.getCategorieClient().getIdCategorieClient(),
                remiseCategorie.getTarif(),
                remiseCategorie.getPourcentageDeReduction()
        );
    }

    private RemiseCategorie convertToEntity(RemiseCategorieDTO remiseCategorieDTO) {
        RemiseCategorie remiseCategorie = new RemiseCategorie();
        if (remiseCategorieDTO.getIdRemise() != null) {
            remiseCategorie.setIdRemise(remiseCategorieDTO.getIdRemise());
        }
        remiseCategorie.setTarif(remiseCategorieDTO.getTarif());
        remiseCategorie.setPourcentageDeReduction(remiseCategorieDTO.getPourcentageDeReduction());

        TypePlace typePlace = typePlaceRepository.findById(remiseCategorieDTO.getIdTypePlace()).orElse(null);
        remiseCategorie.setTypePlace(typePlace);

        CategorieClient categorieClient = categorieCategoryRepository.findById(remiseCategorieDTO.getIdCategorieClient()).orElse(null);
        remiseCategorie.setCategorieClient(categorieClient);

        return remiseCategorie;
    }
}
