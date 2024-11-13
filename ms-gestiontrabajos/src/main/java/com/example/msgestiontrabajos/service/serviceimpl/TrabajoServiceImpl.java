package com.example.msgestiontrabajos.service.serviceimpl;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.repository.TrabajoRepository;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServiceImpl implements TrabajoService {

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Override
    public List<Trabajo> list() {
        return trabajoRepository.findAll();
    }

    @Override
    public Optional<Trabajo> getById(Integer id) {
        return trabajoRepository.findById(id);
    }

    @Override
    public Trabajo save(Trabajo trabajo) {
        trabajo.setFechaPublicacion(LocalDate.now().toString());
        return trabajoRepository.save(trabajo);
    }

    @Override
    public Trabajo update(Trabajo trabajo) {
        return trabajoRepository.save(trabajo);
    }

    @Override
    public void delete(Integer id) {
        trabajoRepository.deleteById(id);
    }

    @Override
    public List<Trabajo> findByEmpresaId(Integer empresaId) {
        return trabajoRepository.findByEmpresaId(empresaId);
    }
}
