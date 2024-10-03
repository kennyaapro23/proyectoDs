package com.example.msempresa.feign;


import com.example.msempresa.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-user-service", path = "/user")

public interface UserFeign {
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Integer id);

}
