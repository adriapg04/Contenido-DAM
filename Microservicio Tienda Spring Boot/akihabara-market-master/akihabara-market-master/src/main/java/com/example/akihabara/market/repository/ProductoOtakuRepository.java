package com.example.akihabara.market.repository;

import com.example.akihabara.market.entity.ProductoOtaku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para manejar operaciones CRUD de ProductoOtaku
 * Spring Data JPA implementa automaticamente metodos como:
 * - findAll()
 * - findById(ID)
 * - save(entity)
 * - deleteById(ID)
 */
@Repository
public interface ProductoOtakuRepository extends JpaRepository<ProductoOtaku, Long> {

    /**
     * Buscar productos por categoria (insensible a mayusculas/minusculas)
     */
    List<ProductoOtaku> findByCategoriaIgnoreCase(String categoria);

    /**
     * Buscar productos que contengan el texto en el nombre (insensible a mayusculas/minusculas)
     */
    List<ProductoOtaku> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Buscar productos con stock menor que la cantidad dada
     */
    List<ProductoOtaku> findByStockLessThan(Integer cantidad);

}
