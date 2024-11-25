package com.example.msempresa.feign;

import com.example.msempresa.dto.AuthUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-auth-service", path = "/auth")
public interface AuthUserFeign {

        @GetMapping("/{id}")
        ResponseEntity<AuthUserDto> getById(@PathVariable("id") Integer id);
}
