package com.example.mspostulaciones.service;



import com.example.mspostulaciones.entity.Postulacion;

import java.util.List;
import java.util.Optional;

public interface PostulacionService {
    public List<Postulacion> list();

    public Optional<Postulacion> getById(Integer id);

    public Postulacion save(Postulacion postulacion);

    public Postulacion update(Integer id, Postulacion postulacion);

    public void delete(Integer id);
}
