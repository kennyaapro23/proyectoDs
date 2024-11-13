package com.example.msempresa.entity;

import com.example.msempresa.dto.GestiontrabajosDto;

import jakarta.persistence.*;
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
    private String correo; // correo de la empresa donde le llegaran las notificaciones
    private String rubro; // rubro en SQL
    private String creadoPor; // creado_por en SQL
    private String actualizadoPor; // actualizado_por en SQL
    private Integer gestiontrabajosid;

    @Transient
    private GestiontrabajosDto gestiontrabajosDto;


}
