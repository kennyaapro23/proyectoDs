package com.example.msgestiontrabajos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Trabajos {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tituloTrabajo;
    private String empresa;
    private String fechaInicio;
    private String fechaFin;
    private String descripcion;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
    private String responsable;

}
