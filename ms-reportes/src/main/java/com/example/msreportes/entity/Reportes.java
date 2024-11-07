package com.example.msreportes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Reportes {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer usuarioId; // Relación con el usuario (empleador o candidato)
    private Integer trabajoId; // Relación con el trabajo (vacante)
    private String fechaPostulacion; // Fecha en la que el candidato postuló
    private String estado; // Ej. "Pendiente", "Aceptado", "Rechazado"
    private String comentario; // Comentarios adicionales, si los hay

    // Puedes agregar relaciones si lo deseas, por ejemplo:
    // @ManyToOne
    // @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    // private Usuario usuario;
    //
    // @ManyToOne
    // @JoinColumn(name = "trabajo_id", referencedColumnName = "id")
    // private Trabajo trabajo;
}
