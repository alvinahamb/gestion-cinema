package com.example.demo.repository;

import com.example.demo.entity.Publicite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PubliciteRepository extends JpaRepository<Publicite, Integer> {
}
