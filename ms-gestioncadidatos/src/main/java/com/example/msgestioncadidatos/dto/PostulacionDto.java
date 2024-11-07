package com.example.msgestioncadidatos.dto;

import lombok.Data;

@Data
public class PostulacionDto {
    private Integer id;
    private Integer usuarioId; // Relación con el usuario
    private Integer trabajoId; // Relación con el trabajo
    private String fechaPostulacion;
    private String estado; // Ej. "Pendiente", "Aceptado", "Rechazado"
    private String comentario; // Comentarios adicionales, si los hay
}
