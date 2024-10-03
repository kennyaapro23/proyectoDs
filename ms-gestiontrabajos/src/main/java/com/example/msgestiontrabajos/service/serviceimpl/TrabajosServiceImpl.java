package com.example.msgestiontrabajos.service.serviceimpl;



import com.example.msgestiontrabajos.entity.Trabajos;
import com.example.msgestiontrabajos.repository.TrabajosRepository;
import com.example.msgestiontrabajos.service.TrabajosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class TrabajosServiceImpl implements TrabajosService {

    @Autowired
    private TrabajosRepository trabajosRepository;
    @Override
    public List<Trabajos> listar() {
        return trabajosRepository.findAll();
    }

    @Override
    public Optional<Trabajos> listarPorId(Integer id) {
        return trabajosRepository.findById(id);
    }

    @Override
    public Trabajos guardar(Trabajos trabajos) {
        return trabajosRepository.save(trabajos);
    }

    @Override
    public Trabajos actualizar(Trabajos trabajos) {
        return trabajosRepository.save(trabajos);
    }

    @Override
    public void eliminar(Integer id) {
        trabajosRepository.deleteById(id);
    }
}
