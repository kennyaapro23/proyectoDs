package com.example.msempresa.feign;

import com.example.msempresa.dto.GestiontrabajosDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@FeignClient(name = "ms-gestiontrabajos-service", path = "/trabajos")

public interface GestiontrabajosFeign {



        @GetMapping("/{id}")
        ResponseEntity<GestiontrabajosDto> getById(@PathVariable("id") Integer id);

        @PostMapping
        ResponseEntity<GestiontrabajosDto> publicarTrabajo(@RequestBody GestiontrabajosDto trabajoDto);
}
