package com.example.mspostulaciones.feign;

import com.example.mspostulaciones.dto.CandidatoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-gestioncandidatos-service", path = "/gestioncandidatos")
public interface GestionCandidatosFeign {
    @GetMapping("/{id}")
    CandidatoDto obtenerCandidatoPorId(@PathVariable("id") Integer id);
}
