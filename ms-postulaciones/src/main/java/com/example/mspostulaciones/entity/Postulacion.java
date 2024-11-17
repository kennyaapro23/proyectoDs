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

    @Column(name = "candidatos_id")
    private Integer candidatosId;

    @Column(name = "trabajo_id")
    private Integer trabajoId;

    @Column(name = "fecha_postulacion")
    private String fechaPostulacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "comentario", length = 500)
    private String comentario;
}