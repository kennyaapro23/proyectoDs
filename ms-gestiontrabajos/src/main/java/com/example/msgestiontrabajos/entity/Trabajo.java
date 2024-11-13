package com.example.msgestiontrabajos.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabajos")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "empresa_id")
    private Integer empresaId; // Relacionado con la empresa que publica el trabajo

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "tipo_contrato")
    private String tipoContrato; // Ejemplo: "Tiempo completo", "Parcial", "Freelance"

    @Column(name = "salario")
    private String salario;

    @Column(name = "fecha_publicacion")
    private String fechaPublicacion;
}
