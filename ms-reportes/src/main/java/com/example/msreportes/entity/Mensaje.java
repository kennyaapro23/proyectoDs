package com.example.msreportes.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mensajes")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long conversacionId; // <--- Asegúrate de que este campo esté presente
    private String tipoRemitente;
    private Long remitenteId;
    private String remitenteNombre;

    private String tipoDestinatario;
    private Long destinatarioId;
    private String destinatarioNombre;

    private String contenido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;

    private boolean leido;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversacionId() {
        return conversacionId;
    }

    public void setConversacionId(Long conversacionId) {
        this.conversacionId = conversacionId;
    }

    public String getTipoRemitente() {
        return tipoRemitente;
    }

    public void setTipoRemitente(String tipoRemitente) {
        this.tipoRemitente = tipoRemitente;
    }

    public Long getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Long remitenteId) {
        this.remitenteId = remitenteId;
    }

    public String getRemitenteNombre() {
        return remitenteNombre;
    }

    public void setRemitenteNombre(String remitenteNombre) {
        this.remitenteNombre = remitenteNombre;
    }

    public String getTipoDestinatario() {
        return tipoDestinatario;
    }

    public void setTipoDestinatario(String tipoDestinatario) {
        this.tipoDestinatario = tipoDestinatario;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getDestinatarioNombre() {
        return destinatarioNombre;
    }

    public void setDestinatarioNombre(String destinatarioNombre) {
        this.destinatarioNombre = destinatarioNombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}
