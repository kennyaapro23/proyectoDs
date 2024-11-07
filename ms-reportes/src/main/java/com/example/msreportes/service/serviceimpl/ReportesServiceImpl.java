package com.example.msreportes.service.serviceimpl;

import com.example.msreportes.entity.Reportes;

import com.example.msreportes.repository.ReportesRepository;
import com.example.msreportes.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ReportesServiceImpl implements ReportesService {
    @Autowired
    private ReportesRepository reportesRepository;

    @Override
    public List<Reportes> list() {
        return reportesRepository.findAll();
    }

    @Override
    public Optional<Reportes> getById(Integer id) {
        return reportesRepository.findById(id);
    }

    @Override
    public Reportes save(Reportes reportes) {
        return reportesRepository.save(reportes);
    }

    @Override
    public Reportes update(Integer id, Reportes reportes) {
        reportes.setId(id);
        return reportesRepository.save(reportes);
    }

    @Override
    public void delete(Integer id) {
        reportesRepository.deleteById(id);
    }

}
