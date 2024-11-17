package com.example.msgestioncadidatos.controller;

import com.example.msgestioncadidatos.entity.GestionCandidatos;
import com.example.msgestioncadidatos.service.GestionCandidatosService;
import jakarta.validation.Valid;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gestioncandidatos")
public class GestionCandidatosController {

    @Autowired
    private GestionCandidatosService gestionCandidatosService;

    // Listar todos los candidatos
    @GetMapping
    public ResponseEntity<List<GestionCandidatos>> list() {
        List<GestionCandidatos> candidatos = gestionCandidatosService.list();
        return candidatos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(candidatos);
    }

    // Obtener candidato por ID
    @GetMapping("/{id}")
    public ResponseEntity<GestionCandidatos> findById(@PathVariable Integer id) {
        return gestionCandidatosService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear un nuevo candidato
    @PostMapping
    public ResponseEntity<GestionCandidatos> save(@Valid @RequestBody GestionCandidatos gestionCandidatos) {
        try {
            GestionCandidatos nuevoCandidato = gestionCandidatosService.save(gestionCandidatos);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCandidato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar un candidato existente
    @PutMapping("/{id}")
    public ResponseEntity<GestionCandidatos> update(
            @PathVariable Integer id,
            @Valid @RequestBody GestionCandidatos gestionCandidatos) {
        return gestionCandidatosService.getById(id)
                .map(existing -> {
                    gestionCandidatos.setId(id);
                    GestionCandidatos actualizado = gestionCandidatosService.update(gestionCandidatos);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar un candidato por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (gestionCandidatosService.getById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato no encontrado");
        }
        gestionCandidatosService.delete(id);
        return ResponseEntity.ok("Eliminación correcta");
    }

    // Enviar código de verificación
    @PostMapping("/enviar-codigo")
    public ResponseEntity<String> enviarCodigo(@RequestParam String email) {
        try {
            gestionCandidatosService.enviarCodigoVerificacion(email);
            return ResponseEntity.ok("Código enviado al correo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el código.");
        }
    }

    // Verificar código
    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(
            @RequestParam String email,
            @RequestParam String codigo) {
        try {
            if (gestionCandidatosService.verificarCodigo(email, codigo)) {
                return ResponseEntity.ok("Correo verificado exitosamente.");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código incorrecto.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo de confirmación.");
        }
    }

    // Reenviar código de verificación
    @PostMapping("/reenviar-codigo")
    public ResponseEntity<String> reenviarCodigo(@RequestParam String email) {
        try {
            gestionCandidatosService.reenviarCodigoVerificacion(email);
            return ResponseEntity.ok("Nuevo código enviado al correo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al reenviar el código.");
        }
    }
}
