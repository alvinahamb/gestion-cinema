package com.example.demo.service;

import com.example.demo.dto.CategorieClientDTO;
import com.example.demo.dto.PlaceDTO;
import com.example.demo.dto.SalleDTO;
import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.TicketPlaceDTO;
import com.example.demo.dto.TypePlaceDTO;
import com.example.demo.entity.CategorieClient;
import com.example.demo.entity.Place;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.TicketPlace;
import com.example.demo.repository.CategorieClientRepository;
import com.example.demo.repository.PlaceRepository;
import com.example.demo.repository.TicketPlaceRepository;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketPlaceService {
    @Autowired
    private TicketPlaceRepository ticketPlaceRepository;
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private PlaceRepository placeRepository;
    
    @Autowired
    private CategorieClientRepository categorieClientRepository;

    public List<TicketPlaceDTO> getAllTicketPlaces() {
        return ticketPlaceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TicketPlaceDTO> getTicketPlaceById(Integer id) {
        return ticketPlaceRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<TicketPlaceDTO> getByTicketId(Integer ticketId) {
        return ticketPlaceRepository.findAll().stream()
                .filter(tp -> tp.getTicket() != null && tp.getTicket().getIdTicket().equals(ticketId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TicketPlaceDTO createTicketPlace(TicketPlaceDTO dto) {
        TicketPlace entity = convertToEntity(dto);
        TicketPlace saved = ticketPlaceRepository.save(entity);
        return convertToDTO(saved);
    }

    public TicketPlaceDTO updateTicketPlace(Integer id, TicketPlaceDTO dto) {
        Optional<TicketPlace> existing = ticketPlaceRepository.findById(id);
        if (existing.isPresent()) {
            TicketPlace entity = existing.get();
            
            if (dto.getTicket() != null && dto.getTicket().getIdTicket() != null) {
                ticketRepository.findById(dto.getTicket().getIdTicket())
                    .ifPresent(entity::setTicket);
            }
            
            if (dto.getPlace() != null && dto.getPlace().getIdPlace() != null) {
                placeRepository.findById(dto.getPlace().getIdPlace())
                    .ifPresent(entity::setPlace);
            }
            
            if (dto.getCategorieClient() != null && dto.getCategorieClient().getIdCategorieClient() != null) {
                categorieClientRepository.findById(dto.getCategorieClient().getIdCategorieClient())
                    .ifPresent(entity::setCategorieClient);
            }
            
            entity.setPrix(dto.getPrix());
            TicketPlace updated = ticketPlaceRepository.save(entity);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deleteTicketPlace(Integer id) {
        if (ticketPlaceRepository.existsById(id)) {
            ticketPlaceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private TicketPlaceDTO convertToDTO(TicketPlace entity) {
        TicketDTO ticketDTO = null;
        if (entity.getTicket() != null) {
            ticketDTO = new TicketDTO();
            ticketDTO.setIdTicket(entity.getTicket().getIdTicket());
        }
        
        PlaceDTO placeDTO = null;
        if (entity.getPlace() != null) {
            Place place = entity.getPlace();
            placeDTO = new PlaceDTO(place.getIdPlace(), place.getSalle().getIdSalle(), place.getTypePlace().getIdTypePlace(), 
                                   place.getNumeroLigne(), place.getNumeroColonne(), BigDecimal.ZERO);
        }
        
        CategorieClientDTO categorieClientDTO = null;
        if (entity.getCategorieClient() != null) {
            CategorieClient cc = entity.getCategorieClient();
            categorieClientDTO = new CategorieClientDTO(cc.getIdCategorieClient(), 
                                                        cc.getLibelle(), cc.getDescription());
        }
        
        return new TicketPlaceDTO(
            entity.getId(),
            ticketDTO,
            placeDTO,
            categorieClientDTO,
            entity.getPrix()
        );
    }

    private TicketPlace convertToEntity(TicketPlaceDTO dto) {
        Ticket ticket = null;
        if (dto.getTicket() != null && dto.getTicket().getIdTicket() != null) {
            ticket = ticketRepository.findById(dto.getTicket().getIdTicket()).orElse(null);
        }
        
        Place place = null;
        if (dto.getPlace() != null && dto.getPlace().getIdPlace() != null) {
            place = placeRepository.findById(dto.getPlace().getIdPlace()).orElse(null);
        }
        
        CategorieClient categorieClient = null;
        if (dto.getCategorieClient() != null && dto.getCategorieClient().getIdCategorieClient() != null) {
            categorieClient = categorieClientRepository.findById(dto.getCategorieClient().getIdCategorieClient()).orElse(null);
        }
        
        return new TicketPlace(ticket, place, categorieClient, dto.getPrix());
    }
}
