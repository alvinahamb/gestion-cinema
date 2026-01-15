package com.example.demo.repository;

import com.example.demo.entity.ReservationPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationPlaceRepository extends JpaRepository<ReservationPlace, Integer> {
    List<ReservationPlace> findByReservationIdReservation(Integer reservationId);
    
    @Query("SELECT rp.place.idPlace FROM ReservationPlace rp WHERE rp.reservation.seance.idSeance = :seanceId")
    List<Integer> findOccupiedPlaceIdsBySeanceId(@Param("seanceId") Integer seanceId);
}
