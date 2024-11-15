package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class TrabajoDto {
    private Integer id;
    private String titulo;
    private String empresaId;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
    private String fechaPublicacion;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private String empresaDto;

}