package com.example.demo.repository;

import com.example.demo.entity.PaiementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface PaiementDetailRepository extends JpaRepository<PaiementDetail, Integer> {
    
    @Query("SELECT COALESCE(SUM(pd.montant), 0) FROM PaiementDetail pd " +
           "JOIN pd.paiement p " +
           "JOIN p.ticket t " +
           "WHERE t.seance.idSeance = :seanceId")
    BigDecimal sumMontantBySeanceId(@Param("seanceId") Integer seanceId);
}
