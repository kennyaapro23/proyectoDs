package com.example.mspostulaciones.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Postulacion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer usuarioId; // Relación con el usuario
    private Integer trabajoId; // Relación con el trabajo
    private String fechaPostulacion;
    private String estado; // Ej. "Pendiente", "Aceptado", "Rechazado"
    private String comentario; // Comentarios adicionales, si los hay

}
