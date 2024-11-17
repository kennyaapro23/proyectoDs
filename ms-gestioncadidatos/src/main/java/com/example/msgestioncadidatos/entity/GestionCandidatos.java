package com.example.msgestioncadidatos.entity;


import com.example.msgestioncadidatos.dto.PostulacionDto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "gestion_candidatos")
public class GestionCandidatos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Información personal */
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombreCompleto;

    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @Pattern(regexp = "^(M|F|O)$", message = "El género debe ser 'M', 'F' o 'O' (Otro)")
    private String genero;

    /** Información de identificación y contacto */
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(min = 8, max = 12, message = "El número de documento debe tener entre 8 y 12 caracteres")
    private String numeroDocumento;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 50, message = "La nacionalidad no puede exceder los 50 caracteres")
    private String nacionalidad;

    @Pattern(regexp = "^[0-9]{9}$", message = "El número de teléfono debe tener 9 dígitos")
    private String telefono;

    @Email(message = "El correo electrónico no tiene un formato válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String correoElectronico;

    @Size(max = 200, message = "La dirección no puede exceder los 200 caracteres")
    private String direccion;

    /** Información adicional del perfil */
    private String fotoPerfil; // URL a la foto de perfil

    /** Información académica y profesional */
    @NotBlank(message = "La carrera es obligatoria")
    private String carrera;

    @Size(max = 100, message = "La especialidad no puede exceder los 100 caracteres")
    private String especialidad;

    @NotBlank(message = "El nivel de estudios es obligatorio")
    private String nivelEstudios; // Ejemplo: "Bachiller", "Titulado", "Maestría"

    @NotBlank(message = "La experiencia laboral es obligatoria")
    private String experienciaLaboral; // Resumen de experiencia laboral en años o puestos previos

    @ElementCollection
    private List<String> habilidades; // Ej: ["Java", "Spring Boot", "SQL", "Trabajo en equipo"]

    private String cv; // Ruta al archivo del CV del candidato

    /** Información sobre disponibilidad y preferencias laborales */
    private String disponibilidad; // Ej: "Inmediata", "1 mes"
    private String tipoContratoDeseado; // Ej: "Tiempo completo", "Freelance", "Prácticas"
    private String pretensionSalarial; // Ej: "2000 - 3000 soles"
    private String ubicacionPreferida; // Ej: "Lima", "Remoto"

    /** Información sobre postulaciones previas */
    private Integer postulacionId;
    @Transient
    private PostulacionDto postulacionDto;

    @Column(length = 6)
    private String codigoVerificacion;

    private Boolean emailVerificado = false;

    /**
     * Genera una descripción detallada del candidato
     * @return Descripción completa del candidato
     */
    public String generarDescripcion() {
        return String.format(
                "Candidato: %s\n" +
                        "Fecha de Nacimiento: %s\n" +
                        "Género: %s\n" +
                        "Documento: %s\n" +
                        "Nacionalidad: %s\n" +
                        "Teléfono: %s\n" +
                        "Correo Electrónico: %s\n" +
                        "Dirección: %s\n" +
                        "Carrera: %s\n" +
                        "Especialidad: %s\n" +
                        "Nivel de Estudios: %s\n" +
                        "Experiencia Laboral: %s\n" +
                        "Habilidades: %s\n" +
                        "Disponibilidad: %s\n" +
                        "Tipo de Contrato Deseado: %s\n" +
                        "Pretensión Salarial: %s\n" +
                        "Ubicación Preferida: %s\n",
                nombreCompleto,
                fechaNacimiento != null ? fechaNacimiento.toString() : "N/A",
                genero,
                numeroDocumento,
                nacionalidad,
                telefono,
                correoElectronico,
                direccion,
                carrera,
                especialidad,
                nivelEstudios,
                experienciaLaboral,
                habilidades != null ? String.join(", ", habilidades) : "N/A",
                disponibilidad,
                tipoContratoDeseado,
                pretensionSalarial,
                ubicacionPreferida
        );
    }
}
