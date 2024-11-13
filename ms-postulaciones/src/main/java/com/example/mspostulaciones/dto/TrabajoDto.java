package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class TrabajoDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
}