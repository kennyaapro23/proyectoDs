package com.example.mspostulaciones.feign;


import com.example.mspostulaciones.dto.TrabajoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-gestiontrabajo", path = "/trabajo")
public interface TrabajoFeign {
    @GetMapping("/{id}")
    TrabajoDto obtenerTrabajoPorId(@PathVariable("id") Integer id);
}