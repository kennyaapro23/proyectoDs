package com.example.msgestioncadidatos.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String location;
}
