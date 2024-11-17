package com.example.msreportes.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversaciones")
public class Conversacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long remitenteId;
    private Long destinatarioId;
    private String tipoRemitente;
    private String tipoDestinatario;
    private Date fechaCreacion;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Long remitenteId) { this.remitenteId = remitenteId; }

    public Long getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Long destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getTipoRemitente() { return tipoRemitente; }
    public void setTipoRemitente(String tipoRemitente) { this.tipoRemitente = tipoRemitente; }

    public String getTipoDestinatario() { return tipoDestinatario; }
    public void setTipoDestinatario(String tipoDestinatario) { this.tipoDestinatario = tipoDestinatario; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
