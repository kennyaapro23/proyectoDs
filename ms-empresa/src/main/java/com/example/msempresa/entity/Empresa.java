package com.example.msempresa.entity;

import com.example.msempresa.dto.AuthUserDto;
import com.example.msempresa.dto.GestiontrabajosDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Solo almacenamos el ID del usuario
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @NotBlank(message = "El nombre de la empresa no puede estar vacío")
    @Column(name = "nombre_empresa", nullable = false, length = 100)
    private String nombreEmpresa;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "sitio_web", length = 200)
    private String sitioWeb;

    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "rubro", length = 100)
    private String rubro;


    @Column(name = "gestiontrabajos_id")
    private Integer gestiontrabajosId;

    // Campos transitorios para datos relacionados no almacenados en la base de datos
    @Transient
    private GestiontrabajosDto gestiontrabajosDto;

    @Transient
    private AuthUserDto authUserDto;

}
