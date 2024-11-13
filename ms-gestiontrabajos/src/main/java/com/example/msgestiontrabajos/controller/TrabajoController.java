package com.example.msgestiontrabajos.controller;

import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    @GetMapping
    public ResponseEntity<List<Trabajo>> listar() {
        return ResponseEntity.ok(trabajoService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Trabajo>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(trabajoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Trabajo> crear(@RequestBody Trabajo trabajo) {
        return ResponseEntity.ok(trabajoService.save(trabajo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trabajo> actualizar(@PathVariable Integer id, @RequestBody Trabajo trabajo) {
        trabajo.setId(id);
        return ResponseEntity.ok(trabajoService.update(trabajo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        trabajoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Trabajo>> obtenerPorEmpresa(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(trabajoService.findByEmpresaId(empresaId));
    }
}
