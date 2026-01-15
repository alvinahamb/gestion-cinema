package com.example.demo.service;

import com.example.demo.dto.StatusDTO;
import com.example.demo.dto.TypeStatutDTO;
import com.example.demo.entity.Status;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.TypeStatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TypeStatutRepository typeStatutRepository;

    public List<StatusDTO> getAllStatus() {
        return statusRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<StatusDTO> getStatusById(Integer id) {
        return statusRepository.findById(id)
                .map(this::convertToDTO);
    }

    public StatusDTO createStatus(StatusDTO statusDTO) {
        Status status = convertToEntity(statusDTO);
        Status savedStatus = statusRepository.save(status);
        return convertToDTO(savedStatus);
    }

    public StatusDTO updateStatus(Integer id, StatusDTO statusDTO) {
        Optional<Status> existingStatus = statusRepository.findById(id);
        if (existingStatus.isPresent()) {
            Status status = existingStatus.get();
            status.setLibelle(statusDTO.getLibelle());
            status.setCode(statusDTO.getCode());
            status.setOrdre(statusDTO.getOrdre());
            if (statusDTO.getTypeStatut() != null) {
                typeStatutRepository.findById(statusDTO.getTypeStatut().getIdTypeStatut()).ifPresent(status::setTypeStatut);
            }
            Status updatedStatus = statusRepository.save(status);
            return convertToDTO(updatedStatus);
        }
        return null;
    }

    public boolean deleteStatus(Integer id) {
        if (statusRepository.existsById(id)) {
            statusRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private StatusDTO convertToDTO(Status status) {
        TypeStatutDTO typeStatut = status.getTypeStatut() != null ? 
            new TypeStatutDTO(status.getTypeStatut().getIdTypeStatut(), status.getTypeStatut().getLibelle()) : null;
        return new StatusDTO(status.getIdStatus(), status.getLibelle(), status.getCode(), status.getOrdre(), typeStatut);
    }

    private Status convertToEntity(StatusDTO statusDTO) {
        Status status = new Status(statusDTO.getLibelle(), statusDTO.getCode(), statusDTO.getOrdre(), null);
        if (statusDTO.getTypeStatut() != null) {
            typeStatutRepository.findById(statusDTO.getTypeStatut().getIdTypeStatut()).ifPresent(status::setTypeStatut);
        }
        return status;
    }
}
