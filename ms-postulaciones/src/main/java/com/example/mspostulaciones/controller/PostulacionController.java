package com.example.mspostulaciones.controller;


import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @PostMapping
    public Postulacion postular(@RequestBody Postulacion postulacion) {
        return postulacionService.save(postulacion);
    }

    @GetMapping
    public List<Postulacion> listar() {
        return postulacionService.list();
    }
}
