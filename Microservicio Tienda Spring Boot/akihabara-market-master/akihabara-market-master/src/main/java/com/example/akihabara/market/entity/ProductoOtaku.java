package com.example.akihabara.market.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidad que representa un producto otaku en la tienda de Maestro Tanaka
 * Cada producto tiene un ID único, nombre, categoria, precio y cantidad en stock
 */
@Entity
@Table(name = "producto_otaku")
public class ProductoOtaku {

    // ID unico del producto (clave primaria, se genera automaticamente)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto (obligatorio, maximo 100 caracteres)
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    // Categoria del producto (Figura, Manga, Poster, etc.)
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String categoria;

    // Precio del producto (debe ser positivo)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    // Cantidad en stock (no puede ser negativa)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    // Constructor vacio (requerido por JPA)
    public ProductoOtaku() {}

    // Constructor con parametros
    public ProductoOtaku(String nombre, String categoria, Double precio, Integer stock) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductoOtaku{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}