package com.example.msgestiontrabajos.service;





import com.example.msgestiontrabajos.entity.Trabajos;

import java.util.List;
import java.util.Optional;

public interface TrabajosService {
    public List<Trabajos> listar();
    public Optional<Trabajos> listarPorId(Integer id);
    public Trabajos guardar(Trabajos trabajos);
    public Trabajos actualizar(Trabajos trabajos);
    public void eliminar(Integer id);
}
