package com.example.practicaspringgitportfolio.service;

import com.example.practicaspringgitportfolio.model.Lenguaje;
import com.example.practicaspringgitportfolio.repository.LenguajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LenguajeService {

    @Autowired
    private LenguajeRepository lenguajeRepository;

    public List<Lenguaje> getAllLenguajes() {
        return lenguajeRepository.findAll();
    }

    public Lenguaje getLenguajeById(Long id) {
        return lenguajeRepository.findById(id).orElse(null);
    }
}
