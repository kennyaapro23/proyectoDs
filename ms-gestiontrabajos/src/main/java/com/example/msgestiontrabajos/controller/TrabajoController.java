package com.example.msgestiontrabajos.controller;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.dto.response.CloudinaryResponse;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.service.TrabajoService;
import com.example.msgestiontrabajos.service.CloudinaryService; // Servicio de Cloudinary
import com.example.msgestiontrabajos.util.FileUploadUtil; // Utilidad para validaciones
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trabajos")
public class TrabajoController {

    @Autowired
    private TrabajoService trabajoService;

    @Autowired
    private CloudinaryService cloudinaryService; // Inyectar servicio de Cloudinary

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
    public ResponseEntity<Object> subirImagen(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            // Validar el archivo
            FileUploadUtil.assertAllowed(file, FileUploadUtil.IMAGE_PATTERN);

            // Subir la imagen a Cloudinary
            String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());
            CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);

            // Obtener el trabajo desde el servicio
            Optional<Trabajo> trabajoOpt = trabajoService.getById(id);
            if (trabajoOpt.isPresent()) {
                Trabajo trabajo = trabajoOpt.get();
                trabajo.setFototrabajo(response.getUrl()); // Guardar la URL en el campo `fototrabajo`
                trabajoService.save(trabajo);
            } else {
                return ResponseEntity.notFound().build(); // Si el trabajo no existe
            }

            return ResponseEntity.ok(response); // Devolver la respuesta completa de Cloudinary
        } catch (FileUploadUtil.FileValidationException e) {
            return ResponseEntity.badRequest().body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir la imagen: " + e.getMessage());
        }
    }
}
