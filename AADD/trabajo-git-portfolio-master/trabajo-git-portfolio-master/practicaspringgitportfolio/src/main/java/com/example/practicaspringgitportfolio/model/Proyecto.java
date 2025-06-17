package com.example.practicaspringgitportfolio.model;

import jakarta.persistence.*;

import java.util.Set;




    @Entity
    public class Proyecto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idProyecto;

        private String nombre;

        @Column(columnDefinition = "TEXT")
        private String descripcion;

        private String imagen;

        private String url;

        @ManyToMany
        @JoinTable(
                name = "proyecto_lenguaje",
                joinColumns = @JoinColumn(name = "id_proyecto"),
                inverseJoinColumns = @JoinColumn(name = "id_lenguaje")
        )
        private Set<Lenguaje> lenguajes;

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Long getIdProyecto() {
            return idProyecto;
        }

        public void setIdProyecto(Long idProyecto) {
            this.idProyecto = idProyecto;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public Set<Lenguaje> getLenguajes() {
            return lenguajes;
        }

        public void setLenguajes(Set<Lenguaje> lenguajes) {
            this.lenguajes = lenguajes;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

