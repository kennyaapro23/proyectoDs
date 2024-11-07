package com.example.msreportes.controller;



import com.example.msreportes.entity.Reportes;
import com.example.msreportes.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reportes")


public class ReportesController {
    @Autowired
    private ReportesService reportesService;

    @GetMapping
    public ResponseEntity<List<Reportes>> list() {
        return ResponseEntity.ok(reportesService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Reportes>> getById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok(reportesService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Reportes> save(@RequestBody Reportes reportes) {
        return ResponseEntity.ok(reportesService.save(reportes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reportes> update(@PathVariable(required = true) Integer id, @RequestBody Reportes reportes) {
        return ResponseEntity.ok(reportesService.update(id, reportes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Reportes>> delete(@PathVariable(required = true) Integer id) {
        reportesService.delete(id);
        return ResponseEntity.ok(reportesService.list());
    }
}
