package com.example.mspostulaciones.dto;

import lombok.Data;

@Data
public class PostulacionDto {
    private Integer id;
    private Integer candidatosId;
    private CandidatoDto candidato;    // Detalles del candidato
    private Integer trabajoId;         // Id del trabajo
    private TrabajoDto trabajo;         // Detalles del trabajo
    private String fechaPostulacion;
    private String estado;
    private String comentario;
    private EmpresaDto empresa;         // Detalles de la empresa
}
