package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class PostulacionDto {
    private Integer id;
    private Integer candidatosId;
    private CandidatoDto candidato;
    private Integer trabajoId;
    private TrabajoDto trabajo; // Detalles del trabajo
    private String fechaPostulacion;
    private String estado;
    private String comentario;


}
