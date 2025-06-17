package com.example.practicaspringgitportfolio.repository;

import com.example.practicaspringgitportfolio.model.Lenguaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LenguajeRepository extends JpaRepository<Lenguaje, Long> {
}
