package com.example.practicaspringgitportfolio.model;

import jakarta.persistence.*;

import java.util.Set;




    @Entity
    public class Lenguaje {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idLenguaje;

        private String nombre;

        private String imagen;

        @ManyToMany(mappedBy = "lenguajes")
        private Set<Proyecto> proyectos;

        public Long getIdLenguaje() {
            return idLenguaje;
        }

        public void setIdLenguaje(Long idLenguaje) {
            this.idLenguaje = idLenguaje;
        }

        public String getImagen() {
            return imagen;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Set<Proyecto> getProyectos() {
            return proyectos;
        }

        public void setProyectos(Set<Proyecto> proyectos) {
            this.proyectos = proyectos;
        }
    }

