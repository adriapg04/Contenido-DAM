package com.example.practicaspringgitportfolio.service;

import com.example.practicaspringgitportfolio.model.Proyecto;
import com.example.practicaspringgitportfolio.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto getProyectoById(Long id) {
        return (Proyecto) proyectoRepository.findById(id).orElse(null);
    }
}
