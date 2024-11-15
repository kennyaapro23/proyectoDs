package com.example.msgestiontrabajos.feign;


import com.example.msgestiontrabajos.dto.EmpresaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "ms-empresa-service", path="/empresa")
public interface TrabajoFeign {

    @GetMapping("/{id}")
    ResponseEntity<EmpresaDto> getById(@PathVariable("id") Integer id);

}
