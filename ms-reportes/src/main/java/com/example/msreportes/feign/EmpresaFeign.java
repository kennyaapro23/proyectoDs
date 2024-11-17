package com.example.msreportes.feign;

import com.example.msreportes.dto.EmpresaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-empresa-service", path="/empresa")
public interface EmpresaFeign {

    @GetMapping("/{id}")
    ResponseEntity<EmpresaDto> getById(@PathVariable("id") Integer id);

}
