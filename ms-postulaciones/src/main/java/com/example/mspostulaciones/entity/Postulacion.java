package com.example.mspostulaciones.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "postulacion")
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id")
    private Integer usuarioId; // Relación con el candidato

    @Column(name = "trabajo_id")
    private Integer trabajoId; // Relación con el trabajo

    @Column(name = "fecha_postulacion")
    private String fechaPostulacion;

    @Column(name = "estado")
    private String estado; // Valores posibles: "Pendiente", "Aceptado", "Rechazado"

    @Column(name = "comentario", length = 500)
    private String comentario; // Comentarios adicionales del candidato o de la empresa
}