package com.example.msgestioncadidatos.service;



import com.example.msgestioncadidatos.entity.GestionCandidatos;

import java.util.List;
import java.util.Optional;

public interface GestionCandidatosService {
    public List<GestionCandidatos> listar();
    public Optional<GestionCandidatos> listarPorId(Integer id);
    public GestionCandidatos guardar(GestionCandidatos gestionCandidatos);
    public GestionCandidatos actualizar(GestionCandidatos gestionCandidatos);
    public void eliminar(Integer id);
}
