package com.example.mspostulaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoDto {
    private Integer id;
    private String descripcion;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
    private LocalDateTime fechaPublicacion;
    private String titulo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Estado estado; // Asegúrate de que este enum está correctamente definido
    private String empresaNombre;
    private String empresaCorreo;
    private Integer empresaId;

    // Enum para el estado del trabajo
    public enum Estado {
        ACTIVO,
        INACTIVO,
        FINALIZADO,
        CANCELADO
    }
}
