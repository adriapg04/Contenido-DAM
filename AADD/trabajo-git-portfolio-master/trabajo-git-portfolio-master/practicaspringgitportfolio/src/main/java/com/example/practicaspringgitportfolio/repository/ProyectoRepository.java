package com.example.practicaspringgitportfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository<Proyecto> extends JpaRepository<Proyecto, Long> {
}
