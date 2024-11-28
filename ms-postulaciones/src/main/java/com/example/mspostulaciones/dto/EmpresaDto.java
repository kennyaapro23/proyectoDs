package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class EmpresaDto {

    private Integer id;
    private String nombreEmpresa;
    private String telefono;
    private String direccion;
    private String descripcion;
    private String sitioWeb;
    private String correo;
    private String rubro;
    private Integer gestionTrabajosId;// Relación con el sistema de gestión de trabajos

}
