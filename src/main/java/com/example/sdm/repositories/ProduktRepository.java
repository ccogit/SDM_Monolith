package com.example.sdm.repositories;

import com.example.sdm.model.Kaese;
import com.example.sdm.model.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long> {
}