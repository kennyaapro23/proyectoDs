package com.example.mspostulaciones.controller;


import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postulacion")


public class PostulacionController {
    @Autowired
    private PostulacionService postulacionService;

    @GetMapping
    public ResponseEntity<List<Postulacion>> list() {
        return ResponseEntity.ok(postulacionService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Postulacion>> getById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(postulacionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Postulacion> save(@RequestBody Postulacion postulacion) {
        return ResponseEntity.ok(postulacionService.save(postulacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postulacion> update(@PathVariable(required = true) Integer id, @RequestBody Postulacion postulacion) {
        return ResponseEntity.ok(postulacionService.update(id, postulacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Postulacion>> delete(@PathVariable(required = true) Integer id) {
        postulacionService.delete(id);
        return ResponseEntity.ok(postulacionService.list());
    }
}
