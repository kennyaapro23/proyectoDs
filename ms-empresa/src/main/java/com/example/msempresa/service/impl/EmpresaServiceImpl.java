package com.example.msempresa.service.impl;

import com.example.msempresa.dto.GestiontrabajosDto;
import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.GestiontrabajosFeign;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private GestiontrabajosFeign gestiontrabajosFeign;

    @Override
    public List<Empresa> list() {
        List<Empresa> empresas = empresaRepository.findAll();

        // Para cada empresa, obtener el GestiontrabajosDto asociado
        for (Empresa empresa : empresas) {
            if (empresa.getGestiontrabajosid() != null) {
                try {
                    // Llamada al Feign Client para obtener el trabajo
                    ResponseEntity<GestiontrabajosDto> response = gestiontrabajosFeign.getById(empresa.getGestiontrabajosid());

                    // Verificar si la respuesta es exitosa (HTTP 200 OK)
                    if (response.getStatusCode().is2xxSuccessful()) {
                        empresa.setGestiontrabajosDto(response.getBody());
                    } else {
                        System.out.println("No se encontró el trabajo para el id: " + empresa.getGestiontrabajosid());
                        empresa.setGestiontrabajosDto(null);
                    }
                } catch (Exception e) {
                    System.out.println("Error al obtener el trabajo para el id: " + empresa.getGestiontrabajosid() + " - " + e.getMessage());
                    empresa.setGestiontrabajosDto(null);
                }
            } else {
                System.out.println("El campo gestiontrabajosid es nulo para la empresa con id: " + empresa.getId());
            }
        }

        return empresas;
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Integer id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
