package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class EmpresaDto {

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

}
