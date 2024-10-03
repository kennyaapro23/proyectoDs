package com.example.msempresa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombreEmpresa; // nombre_empresa en SQL
    private String telefono; // telefono en SQL
    private String direccion; // direccion en SQL
    private String descripcion; // descripcion en SQL
    private String sitioWeb; // sitio_web en SQL
    private String rubro; // rubro en SQL
    private String creadoPor; // creado_por en SQL
    private String actualizadoPor; // actualizado_por en SQL

}
