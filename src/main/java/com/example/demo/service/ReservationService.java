package com.example.demo.service;

import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDTO> getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    public ReservationDTO updateReservation(Integer id, ReservationDTO reservationDTO) {
        Optional<Reservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            Reservation reservation = existingReservation.get();
            reservation.setDateReservation(reservationDTO.getDateReservation());
            reservation.setTotal(reservationDTO.getTotal());
            reservation.setRemise(reservationDTO.getRemise());
            
            Reservation updatedReservation = reservationRepository.save(reservation);
            return convertToDTO(updatedReservation);
        }
        return null;
    }

    public boolean deleteReservation(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
        ClientDTO clientDTO = null;
        if (reservation.getClient() != null) {
            clientDTO = new ClientDTO(reservation.getClient().getIdClient(),
                                     reservation.getClient().getNom(),
                                     reservation.getClient().getPrenom(),
                                     reservation.getClient().getDateNaissance(),
                                     reservation.getClient().getEmail(),
                                     reservation.getClient().getTelephone());
        }
        String statusLibelle = reservation.getStatusReservation() != null ? reservation.getStatusReservation().getLibelle() : null;
        return new ReservationDTO(reservation.getIdReservation(),
                                 clientDTO,
                                 reservation.getSeance() != null ? reservation.getSeance().getIdSeance() : null,
                                 reservation.getDateReservation(),
                                 reservation.getTotal(),
                                 reservation.getRemise(),
                                 reservation.getStatusReservation() != null ? reservation.getStatusReservation().getIdStatus() : null,
                                 statusLibelle);
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setDateReservation(reservationDTO.getDateReservation());
        reservation.setTotal(reservationDTO.getTotal());
        reservation.setRemise(reservationDTO.getRemise());
        return reservation;
    }
}
