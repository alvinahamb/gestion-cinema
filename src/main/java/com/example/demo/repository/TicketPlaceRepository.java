package com.example.demo.repository;

import com.example.demo.entity.TicketPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketPlaceRepository extends JpaRepository<TicketPlace, Integer> {
    List<TicketPlace> findByTicketIdTicket(Integer ticketId);
    
    @Query("SELECT tp FROM TicketPlace tp WHERE tp.ticket.seance.idSeance = :seanceId")
    List<TicketPlace> findBySeanceId(@Param("seanceId") Integer seanceId);
    
    @Query("SELECT tp.place.idPlace FROM TicketPlace tp WHERE tp.ticket.seance.idSeance = :seanceId")
    List<Integer> findOccupiedPlaceIdsBySeanceId(@Param("seanceId") Integer seanceId);
}
