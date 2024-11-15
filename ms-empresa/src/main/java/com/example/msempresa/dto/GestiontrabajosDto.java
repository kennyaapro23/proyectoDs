package com.example.msempresa.dto;

import lombok.Data;

@Data
public class GestiontrabajosDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Integer empresaId;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
    private String fechaPublicacion;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Integer empresaDto;
}