package com.example.msgestioncadidatos.feign;

import com.example.msgestioncadidatos.dto.PostulacionDto;
import com.example.msgestioncadidatos.entity.GestionCandidatos;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "ms-postulacion-service", path = "/candidatos")
public interface PostulacionFeign {

    @GetMapping("/{id}")
    Optional<PostulacionDto> getById(@PathVariable Integer id); // Cambiar a Optional<UserDto>
}