package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class CandidatoDto {
    private Integer id;
    private String nombreCompleto;
    private String fechaNacimiento;
    private String genero;
    private String numeroDocumento;
    private String nacionalidad;
    private String telefono;
    private String correoElectronico;
    private String direccion;
    private String fotoPerfil;
}
