package com.example.msgestiontrabajos.entity;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabajos")
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ID único del trabajo

    @Column(name = "titulo", nullable = false)
    private String titulo; // Título del trabajo

    @Column(name = "descripcion", length = 1000)
    private String descripcion; // Descripción del trabajo

    @Column(name = "empresa_id", nullable = false)
    private Integer empresaId; // Relación con la empresa que publica el trabajo

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion; // Ubicación del trabajo

    @Column(name = "tipo_contrato", nullable = false)
    private String tipoContrato; // Tipo de contrato (ej.: "Tiempo completo", "Freelance")

    @Column(name = "salario")
    private String salario; // Rango salarial o monto del salario

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion; // Fecha de publicación del trabajo

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio; // Fecha de inicio de la oferta laboral

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin; // Fecha de finalización de la oferta laboral

    @Column(name = "fototrabajo")
    private String fototrabajo; // Ruta de la imagen

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado; // Estado de la oferta laboral (ACTIVO o INACTIVO)

    @Transient
    private EmpresaDto empresaDto; // Datos adicionales de la empresa (no se guarda en BD)

    // Enum para el estado del trabajo
    public enum Estado {
        ACTIVO,
        INACTIVO
    }
}
