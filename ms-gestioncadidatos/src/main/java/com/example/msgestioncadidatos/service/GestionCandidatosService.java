package com.example.msgestioncadidatos.service;



import com.example.msgestioncadidatos.entity.GestionCandidatos;

import java.util.List;
import java.util.Optional;

public interface GestionCandidatosService {
    public List<GestionCandidatos> list();
    public Optional<GestionCandidatos> getById(Integer id);
    public GestionCandidatos save(GestionCandidatos gestionCandidatos);
    public GestionCandidatos update(GestionCandidatos gestionCandidatos);
    public void delete(Integer id);
}
