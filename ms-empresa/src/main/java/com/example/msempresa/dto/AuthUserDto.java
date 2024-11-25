package com.example.msempresa.dto;

import com.example.msempresa.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDto {
    private Integer id; // Identificador del usuario
    private String userName;
    private String password;
    private Role role; // Rol del usuario
}
