package com.example.msgestioncadidatos.entity;

import com.example.msgestioncadidatos.dto.UserDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class GestionCandidatos {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
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
    private Integer userid;
    @Transient
    private UserDto userDto;

}
