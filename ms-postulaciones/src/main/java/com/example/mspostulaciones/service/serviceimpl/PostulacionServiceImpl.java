package com.example.mspostulaciones.service.serviceimpl;

import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.repository.PostulacionRepository;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PostulacionServiceImpl implements PostulacionService {
    @Autowired
    private PostulacionRepository postulacionRepository;

    @Override
    public List<Postulacion> list() {
        return postulacionRepository.findAll();
    }

    @Override
    public Optional<Postulacion> getById(Integer id) {
        return postulacionRepository.findById(id);
    }

    @Override
    public Postulacion save(Postulacion postulacion) {
        return postulacionRepository.save(postulacion);
    }

    @Override
    public Postulacion update(Integer id, Postulacion postulacion) {
        postulacion.setId(id);
        return postulacionRepository.save(postulacion);
    }

    @Override
    public void delete(Integer id) {
        postulacionRepository.deleteById(id);
    }

}
