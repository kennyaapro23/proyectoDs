package com.example.msempresa.controller;

import com.example.msempresa.entity.Empresa;
import com.example.msempresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresa")


public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<Empresa>> list() {
        return ResponseEntity.ok(empresaService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empresa>> getById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(empresaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Empresa> save(@RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.save(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable(required = true) Integer id, @RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.update(id, empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Empresa>> delete(@PathVariable(required = true) Integer id) {
        empresaService.delete(id);
        return ResponseEntity.ok(empresaService.list());
    }
}
