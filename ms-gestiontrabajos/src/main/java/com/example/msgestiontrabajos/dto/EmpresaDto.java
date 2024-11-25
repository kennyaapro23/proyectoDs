package com.example.msgestiontrabajos.dto;

import lombok.Data;

@Data
public class EmpresaDto {

    private Integer id; // ID de la empresa
    private String nombreEmpresa; // Nombre de la empresa
    private String telefono; // Teléfono de contacto de la empresa
    private String direccion; // Dirección física de la empresa
    private String descripcion; // Descripción de la empresa
    private String sitioWeb; // URL del sitio web de la empresa
    private String correo; // Correo electrónico para notificaciones
    private String rubro; // Rubro o industria a la que pertenece la empresa
    private Integer gestionTrabajosId; // Relación con el sistema de gestión de trabajos
}