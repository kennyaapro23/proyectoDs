package com.example.msgestioncadidatos.service;

import com.example.msgestioncadidatos.entity.GestionCandidatos;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface GestionCandidatosService {

    // Métodos básicos
    List<GestionCandidatos> list();
    Optional<GestionCandidatos> getById(Integer id);
    GestionCandidatos save(GestionCandidatos gestionCandidatos);
    GestionCandidatos update(GestionCandidatos gestionCandidatos);
    void delete(Integer id);

    // Métodos para la verificación de correo
    void enviarCodigoVerificacion(String email) throws MessagingException;
    boolean verificarCodigo(String email, String codigo) throws MessagingException;
    void reenviarCodigoVerificacion(String email) throws MessagingException; // <-- Agrega esto

}
