package com.example.msempresa.dto;

import lombok.Data;

@Data
public class GestiontrabajosDto {
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

