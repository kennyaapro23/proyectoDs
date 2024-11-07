package com.example.msempresa.service.impl;

import com.example.msempresa.dto.GestiontrabajosDto;
import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.GestiontrabajosFeign;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Empresa> empresa1 = empresaRepository.findAll();

        // Para cada empresa, obtener el GestiontrabajosDto asociado
        for (Empresa empresa : empresa1) {
            // Verifica que el userid no sea nulo
            if (empresa.getId() != null) {
                Optional<GestiontrabajosDto> userDtoOptional = gestiontrabajosFeign.getById(empresa.getGestiontrabajosid()); // Llamada directa

                // Verifica si el GestiontrabajosDto está presente
                if (userDtoOptional.isPresent()) {
                    empresa.setGestiontrabajosDto(userDtoOptional.get()); // Usa el setter para establecer GestiontrabajosDto
                } else {
                    System.out.println("Usuario no encontrado para el id: " + empresa.getGestiontrabajosid());
                    empresa.setGestiontrabajosDto(null); // O asignar un objeto vacío si prefieres
                }
            } else {
                System.out.println("userid es nulo para la empresa con id: " + empresa.getId());
            }
        }

        return empresa1; // Retorna la lista de empresa con GestiontrabajosDto
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
    public Empresa update(Integer id, Empresa user) {
        user.setId(id);
        return empresaRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
