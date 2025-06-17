package com.example.practicaspringgitportfolio.controller;

import com.example.practicaspringgitportfolio.model.Proyecto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.practicaspringgitportfolio.service.ProyectoService;

import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping("/")
    public String mostrarInicio(Model model) {
        List<Proyecto> lista = proyectoService.getAllProyectos();
        model.addAttribute("proyectos", lista);
        return "index";
    }

    @GetMapping("/proyecto/{id}")
    public String mostrarProyecto(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.getProyectoById(id);
        model.addAttribute("proyecto", proyecto);
        return "proyecto";
    }
}
