package com.example.msgestiontrabajos.entity;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabajos")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "empresa_id")
    private Integer empresaId; // Relacionado con la empresa que publica el trabajo

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "tipo_contrato")
    private String tipoContrato; // Ejemplo: "Tiempo completo", "Parcial", "Freelance"

    @Column(name = "salario")
    private String salario;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio; // Fecha y hora de inicio de disponibilidad de la oferta laboral

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin; // Fecha y hora de fin de disponibilidad de la oferta laboral

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado; // Estado de la oferta: ACTIVO o INACTIVO

    // Campo Transient que no se guarda en la base de datos, utilizado para transferir datos de Empresa
    @Transient
    private EmpresaDto empresaDto;

    public enum Estado {
        ACTIVO,
        INACTIVO
    }
}
