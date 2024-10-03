package com.example.msgestioncadidatos.service.serviceimpl;


import com.example.msgestioncadidatos.dto.UserDto;
import com.example.msgestioncadidatos.entity.GestionCandidatos;
import com.example.msgestioncadidatos.feign.UserFeign;
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
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<GestionCandidatos> list() {
        // Obtener todas las gestionCandidatos desde la base de datos
        List<GestionCandidatos> gestionCandidatos1 = gestionCandidatosRepository.findAll();

        // Para cada gestionCandidatos, obtener el UserDto asociado
        for (GestionCandidatos gestionCandidatos : gestionCandidatos1) {
            // Verifica que el userid no sea nulo
            if (gestionCandidatos.getUserid() != null) {
                Optional<UserDto> userDtoOptional = userFeign.getById(gestionCandidatos.getUserid()); // Llamada directa

                // Verifica si el UserDto está presente
                if (userDtoOptional.isPresent()) {
                    gestionCandidatos.setUserDto(userDtoOptional.get()); // Usa el setter para establecer UserDto
                } else {
                    System.out.println("Usuario no encontrado para el id: " + gestionCandidatos.getUserid());
                    gestionCandidatos.setUserDto(null); // O asignar un objeto vacío si prefieres
                }
            } else {
                System.out.println("userid es nulo para la gestionCandidatos con id: " + gestionCandidatos.getId());
            }
        }

        return gestionCandidatos1; // Retorna la lista de gestionCandidatos con UserDto
    }

    @Override
    public Optional<GestionCandidatos> getById(Integer id) {
        return gestionCandidatosRepository.findById(id);
    }

    @Override
    public GestionCandidatos save(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public GestionCandidatos update(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public void delete(Integer id) {
        gestionCandidatosRepository.deleteById(id);
    }

}
