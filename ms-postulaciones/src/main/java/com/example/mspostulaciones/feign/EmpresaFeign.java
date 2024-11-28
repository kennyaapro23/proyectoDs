package com.example.mspostulaciones.feign;

import com.example.mspostulaciones.dto.EmpresaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empresa-service", path="/empresa")
public interface EmpresaFeign {


    @GetMapping("/{id}")
    EmpresaDto obtenerEmpresaPorId(@PathVariable("id") Integer id);

}
