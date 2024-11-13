package com.example.mspostulaciones.feign;

import com.example.mspostulaciones.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-auth", path = "/auth")
public interface UsuarioFeign {
    @GetMapping("/usuario/{id}")
    UsuarioDto obtenerUsuarioPorId(@PathVariable("id") Integer id);
}