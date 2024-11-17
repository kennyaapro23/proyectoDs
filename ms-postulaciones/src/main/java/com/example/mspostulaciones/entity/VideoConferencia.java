package com.example.mspostulaciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_conferencias")
@Data
public class VideoConferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // Identificador único para la sala de videoconferencia
    private String empresaId;
    private String candidatoId;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private String status; // Ej: "pendiente", "en_progreso", "finalizada"

    private String token; // Token para autenticación (si es necesario)
}
