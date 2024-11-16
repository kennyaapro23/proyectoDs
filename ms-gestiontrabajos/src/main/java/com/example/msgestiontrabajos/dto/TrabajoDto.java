package com.example.msgestiontrabajos.dto;

import com.example.msgestiontrabajos.entity.Trabajo; // Asegúrate de que esta línea esté presente
import java.time.LocalDateTime;

public class TrabajoDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String tipoContrato;
    private String salario;
    private LocalDateTime fechaPublicacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Trabajo.Estado estado; // Utiliza el enum Trabajo.Estado correctamente
    private String empresaNombre;
    private String empresaCorreo;

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getTipoContrato() { return tipoContrato; }
    public void setTipoContrato(String tipoContrato) { this.tipoContrato = tipoContrato; }

    public String getSalario() { return salario; }
    public void setSalario(String salario) { this.salario = salario; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Trabajo.Estado getEstado() { return estado; }
    public void setEstado(Trabajo.Estado estado) { this.estado = estado; }

    public String getEmpresaNombre() { return empresaNombre; }
    public void setEmpresaNombre(String empresaNombre) { this.empresaNombre = empresaNombre; }

    public String getEmpresaCorreo() { return empresaCorreo; }
    public void setEmpresaCorreo(String empresaCorreo) { this.empresaCorreo = empresaCorreo; }
}
