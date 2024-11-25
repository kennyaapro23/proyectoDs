package com.example.msgestiontrabajos.feign;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empresa-service", path = "/empresa")
public interface EmpresaFeign {

    // Obtener detalles de una empresa por su ID
    @GetMapping("/{id}")
    EmpresaDto getById(@PathVariable("id") Integer id);
}
