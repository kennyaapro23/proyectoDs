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

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    // Listar todas las empresas
    @GetMapping
    public ResponseEntity<?> list() {
        try {
            List<Empresa> empresas = empresaService.list();
            if (empresas.isEmpty()) {
                return ResponseEntity.status(204).body("No hay empresas registradas.");
            }
            return ResponseEntity.ok(empresas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al listar empresas: " + e.getMessage());
        }
    }

    // Obtener una empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Optional<Empresa> empresa = empresaService.getById(id);
            // Aseguramos que ambos caminos devuelvan el mismo tipo de ResponseEntity
            return empresa.map(e -> ResponseEntity.ok((Object) e))  // Si la empresa existe
                    .orElseGet(() -> ResponseEntity.status(404).body("Empresa no encontrada.")); // Si la empresa no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }


    // Crear una nueva empresa asociando el ID del usuario autenticado
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Empresa empresa) {
        try {
            if (empresa.getUsuarioId() == null) {
                return ResponseEntity.badRequest().body("El campo usuarioId es obligatorio.");
            }
            Empresa savedEmpresa = empresaService.save(empresa, empresa.getUsuarioId());
            return ResponseEntity.status(201).body(savedEmpresa);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al guardar la empresa: " + e.getMessage());
        }
    }

    // Actualizar una empresa por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Empresa empresa) {
        try {
            Empresa updatedEmpresa = empresaService.update(id, empresa);
            return ResponseEntity.ok(updatedEmpresa);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar la empresa: " + e.getMessage());
        }
    }

    // Eliminar una empresa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            empresaService.delete(id);
            return ResponseEntity.ok("Empresa eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la empresa: " + e.getMessage());
        }
    }

    // Publicar un trabajo desde una empresa
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
            return ResponseEntity.status(500).body("Error al publicar el trabajo: " + e.getMessage());
        }
    }

    // Obtener las empresas del usuario autenticado
    @GetMapping("/user")
    public ResponseEntity<?> getEmpresasByUser(@RequestHeader("Authorization") String token) {
        try {
            Integer userId = empresaService.extractUserIdFromToken(token); // Extraer el userId del token
            if (userId == null) {
                return ResponseEntity.status(400).body("Token inválido o usuario no autenticado.");
            }
            List<Empresa> empresas = empresaService.getEmpresasByUserId(userId);
            if (empresas.isEmpty()) {
                return ResponseEntity.status(204).body("No hay empresas asociadas al usuario.");
            }
            return ResponseEntity.ok(empresas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener empresas del usuario: " + e.getMessage());
        }
    }
}
