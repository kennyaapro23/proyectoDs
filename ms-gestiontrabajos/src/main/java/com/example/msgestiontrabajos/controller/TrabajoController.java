package com.example.msgestiontrabajos.controller;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional; // Agregar esta importación


@RestController
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    @Value("${spring.file.upload-dir}") // Ruta definida en application.yml
    private String uploadDir;

    // Listar solo trabajos en estado ACTIVO
    @GetMapping
    public ResponseEntity<List<Trabajo>> listarActivos() {
        List<Trabajo> trabajosActivos = trabajoService.list();
        return ResponseEntity.ok(trabajosActivos);
    }

    // Obtener un trabajo específico por ID con detalles de la empresa
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDto> obtenerPorId(@PathVariable Integer id) {
        EmpresaDto empresaDto = trabajoService.obtenerTrabajoConEmpresa(id);
        if (empresaDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresaDto);
    }

    // Crear un nuevo trabajo para una empresa específica
    @PostMapping("/empresa/{empresaId}")
    public ResponseEntity<Trabajo> crear(@PathVariable Integer empresaId, @RequestBody Trabajo trabajo) {
        trabajo.setEmpresaId(empresaId); // Asignar el ID de la empresa al trabajo
        Trabajo nuevoTrabajo = trabajoService.save(trabajo);
        return ResponseEntity.ok(nuevoTrabajo);
    }

    // Actualizar un trabajo existente
    @PutMapping("/{id}")
    public ResponseEntity<Trabajo> actualizar(@PathVariable Integer id, @RequestBody Trabajo trabajo) {
        trabajo.setId(id);
        Trabajo trabajoActualizado = trabajoService.update(trabajo);
        if (trabajoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trabajoActualizado);
    }

    // Eliminar un trabajo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        trabajoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener todos los trabajos de una empresa específica en estado ACTIVO
    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Trabajo>> obtenerActivosPorEmpresa(@PathVariable Integer empresaId) {
        List<Trabajo> trabajosPorEmpresa = trabajoService.findByEmpresaId(empresaId);
        return ResponseEntity.ok(trabajosPorEmpresa);
    }

    // Endpoint para subir una imagen asociada a un trabajo
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> subirImagen(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            // Verificar si el archivo no está vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío.");
            }

            // Construir la ruta de destino
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Crear el directorio si no existe
            }

            // Guardar el archivo
            String filename = id + "_" + file.getOriginalFilename(); // Ejemplo: "1_logo.png"
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // Devolver la ruta pública de la imagen
            String publicUrl = "/uploads/" + filename;

            // Obtener el trabajo desde el servicio
            Optional<Trabajo> trabajoOpt = trabajoService.getById(id); // Cambié `trabajoService.findById(id)` por `trabajoService.getById(id)`
            if (trabajoOpt.isPresent()) {
                Trabajo trabajo = trabajoOpt.get();
                trabajo.setFototrabajo(publicUrl); // Establecer la ruta de la imagen
                trabajoService.save(trabajo); // Guardar el trabajo con la ruta de la imagen actualizada
            } else {
                return ResponseEntity.notFound().build(); // Si el trabajo no existe
            }

            return ResponseEntity.ok("Imagen subida exitosamente: " + publicUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }

}
