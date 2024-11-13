package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class CandidatoDto {
    private Integer id;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String direccion;
    private String genero;
}
