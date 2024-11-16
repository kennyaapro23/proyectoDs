package com.example.msgestiontrabajos.controller;

import com.example.msgestiontrabajos.dto.TrabajoDto;  // Importar TrabajoDto
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    // Listar solo trabajos en estado ACTIVO
    @GetMapping
    public ResponseEntity<List<Trabajo>> listarActivos() {
        return ResponseEntity.ok(trabajoService.list());
    }

    // Obtener un trabajo específico por ID
    @GetMapping("/{id}")
    public ResponseEntity<TrabajoDto> obtenerPorId(@PathVariable Integer id) {
        TrabajoDto trabajoDto = trabajoService.obtenerTrabajoConEmpresa(id);
        return ResponseEntity.ok(trabajoDto);
    }

    // Crear un nuevo trabajo para una empresa específica
    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<Trabajo> crear(@PathVariable Integer empresaId, @RequestBody Trabajo trabajo) {
        trabajo.setEmpresaId(empresaId);  // Asignar el ID de la empresa al trabajo
        return ResponseEntity.ok(trabajoService.save(trabajo));
    }

    // Actualizar un trabajo existente
    @PutMapping("/{id}")
    public ResponseEntity<Trabajo> actualizar(@PathVariable Integer id, @RequestBody Trabajo trabajo) {
        trabajo.setId(id);
        return ResponseEntity.ok(trabajoService.update(trabajo));
    }

    // Eliminar un trabajo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        trabajoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todos los trabajos de una empresa específica que estén en estado ACTIVO
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Trabajo>> obtenerActivosPorEmpresa(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(trabajoService.findByEmpresaId(empresaId));
    }
}
