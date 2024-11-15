package com.example.msempresa.controller;

import com.example.msempresa.dto.GestiontrabajosDto;
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
    public ResponseEntity<Optional<Empresa>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Empresa empresa) {
        try {
            Empresa savedEmpresa = empresaService.save(empresa);
            return ResponseEntity.status(201).body(savedEmpresa);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al guardar la empresa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(@PathVariable Integer id, @RequestBody Empresa empresa) {
        return ResponseEntity.ok(empresaService.update(id, empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Empresa>> delete(@PathVariable Integer id) {
        empresaService.delete(id);
        return ResponseEntity.ok(empresaService.list());
    }

    // Endpoint para publicar un trabajo
    @PostMapping("/{empresaId}/publicar-trabajo")
    public ResponseEntity<?> publicarTrabajo(
            @PathVariable Integer empresaId,
            @RequestBody GestiontrabajosDto trabajoDto) {
        try {
            GestiontrabajosDto nuevoTrabajo = empresaService.publicarTrabajoDesdeEmpresa(trabajoDto, empresaId);

            if (nuevoTrabajo != null) {
                return ResponseEntity.status(201).body(nuevoTrabajo);
            } else {
                return ResponseEntity.status(500).body("Error al publicar el trabajo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al publicar el trabajo: " + e.getMessage());
        }
    }

}
