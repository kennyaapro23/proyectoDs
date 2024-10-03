package com.example.msgestioncadidatos.controller;


import com.example.msgestioncadidatos.entity.GestionCandidatos;
import com.example.msgestioncadidatos.service.GestionCandidatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestioncandidatos")


public class GestionCandidatosController {
    @Autowired
    private GestionCandidatosService gestionCandidatosService;

    @GetMapping()
    public ResponseEntity<List<GestionCandidatos>> list(){
        return ResponseEntity.ok().body(gestionCandidatosService.list());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GestionCandidatos> findById(@PathVariable(required = true) Integer id){
        return ResponseEntity.ok().body(gestionCandidatosService.getById(id).get());
    }
    @PostMapping()
    public ResponseEntity<GestionCandidatos> save(@RequestBody GestionCandidatos gestionCandidatos){
        return ResponseEntity.ok().body(gestionCandidatosService.save(gestionCandidatos));
    }
    @PutMapping()
    public ResponseEntity<GestionCandidatos> update(@RequestBody GestionCandidatos gestionCandidatos){
        return ResponseEntity.ok().body(gestionCandidatosService.update(gestionCandidatos));
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable(required = true) Integer id){
        gestionCandidatosService.delete(id);
        return "eliminacion correcta";
    }
}
