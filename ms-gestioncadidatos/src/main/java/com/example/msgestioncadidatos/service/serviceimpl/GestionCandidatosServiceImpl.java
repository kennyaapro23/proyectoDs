package com.example.msgestioncadidatos.service.serviceimpl;


import com.example.msgestioncadidatos.entity.GestionCandidatos;
import com.example.msgestioncadidatos.repository.GestionCandidatosRepository;
import com.example.msgestioncadidatos.service.GestionCandidatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class GestionCandidatosServiceImpl implements GestionCandidatosService {

    @Autowired
    private GestionCandidatosRepository gestionCandidatosRepository;
    @Override
    public List<GestionCandidatos> listar() {
        return gestionCandidatosRepository.findAll();
    }

    @Override
    public Optional<GestionCandidatos> listarPorId(Integer id) {
        return gestionCandidatosRepository.findById(id);
    }

    @Override
    public GestionCandidatos guardar(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public GestionCandidatos actualizar(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public void eliminar(Integer id) {
        gestionCandidatosRepository.deleteById(id);
    }
}
