package com.example.msreportes.service;




import com.example.msreportes.entity.Reportes;

import java.util.List;
import java.util.Optional;

public interface ReportesService {
    public List<Reportes> list();

    public Optional<Reportes> getById(Integer id);

    public Reportes save(Reportes reportes);

    public Reportes update(Integer id, Reportes reportes);

    public void delete(Integer id);
}
