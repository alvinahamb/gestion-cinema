package com.example.demo.service;

import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.ReservationDTO;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.SeanceDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.Client;
import com.example.demo.entity.Seance;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.PaiementRepository;
import com.example.demo.repository.PaiementDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private PaiementDetailRepository paiementDetailRepository;

    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TicketDTO> getTicketById(Integer id) {
        return ticketRepository.findById(id)
                .map(this::convertToDTO);
    }

    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);
        Ticket savedTicket = ticketRepository.save(ticket);
        return convertToDTO(savedTicket);
    }

    public TicketDTO updateTicket(Integer id, TicketDTO ticketDTO) {
        Optional<Ticket> existingTicket = ticketRepository.findById(id);
        if (existingTicket.isPresent()) {
            Ticket ticket = existingTicket.get();
            ticket.setDateAchat(ticketDTO.getDateAchat());
            ticket.setTotal(ticketDTO.getTotal());
            ticket.setRemise(ticketDTO.getRemise());
            
            Ticket updatedTicket = ticketRepository.save(ticket);
            return convertToDTO(updatedTicket);
        }
        return null;
    }

    public boolean deleteTicket(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TicketDTO convertToDTO(Ticket ticket) {
        ReservationDTO reservation = null;
        if (ticket.getReservation() != null) {
            reservation = reservationService.getReservationById(ticket.getReservation().getIdReservation()).orElse(null);
        }

        ClientDTO client = null;
        if (ticket.getClient() != null) {
            client = clientService.getClientById(ticket.getClient().getIdClient()).orElse(null);
        }

        SeanceDTO seance = null;
        if (ticket.getSeance() != null) {
            seance = seanceService.getSeanceById(ticket.getSeance().getIdSeance()).orElse(null);
        }

        return new TicketDTO(
            ticket.getIdTicket(),
            reservation,
            client,
            seance,
            ticket.getDateAchat(),
            ticket.getTotal(),
            ticket.getRemise()
        );
    }

    private Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setDateAchat(ticketDTO.getDateAchat());
        ticket.setTotal(ticketDTO.getTotal());
        ticket.setRemise(ticketDTO.getRemise());

        if (ticketDTO.getReservation() != null && ticketDTO.getReservation().getIdReservation() != null) {
            Reservation reservation = new Reservation();
            reservation.setIdReservation(ticketDTO.getReservation().getIdReservation());
            ticket.setReservation(reservation);
        }

        if (ticketDTO.getClient() != null && ticketDTO.getClient().getIdClient() != null) {
            Client client = new Client();
            client.setIdClient(ticketDTO.getClient().getIdClient());
            ticket.setClient(client);
        }

        if (ticketDTO.getSeance() != null && ticketDTO.getSeance().getIdSeance() != null) {
            Seance seance = new Seance();
            seance.setIdSeance(ticketDTO.getSeance().getIdSeance());
            ticket.setSeance(seance);
        }

        return ticket;
    }

    /**
     * Calcule le montant restant à payer pour un ticket
     */
    public BigDecimal getMontantRestantAPayer(Integer ticketId) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
        if (ticketOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        Ticket ticket = ticketOpt.get();
        BigDecimal totalTicket = ticket.getTotal();

        // Trouver le paiement associé au ticket
        Optional<com.example.demo.entity.Paiement> paiementOpt = paiementRepository.findAll().stream()
                .filter(p -> p.getTicket() != null && p.getTicket().getIdTicket().equals(ticketId))
                .findFirst();

        if (paiementOpt.isEmpty()) {
            return totalTicket; // Aucun paiement, retourner le total
        }

        // Calculer le total déjà payé
        BigDecimal totalPaye = paiementDetailRepository.findAll().stream()
                .filter(pd -> pd.getPaiement().getIdPaiement().equals(paiementOpt.get().getIdPaiement()))
                .map(com.example.demo.entity.PaiementDetail::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Retourner le montant restant (total - payé)
        return totalTicket.subtract(totalPaye);
    }
}
