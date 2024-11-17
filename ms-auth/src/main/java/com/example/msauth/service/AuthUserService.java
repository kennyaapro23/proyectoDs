package com.example.msauth.service;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;

public interface AuthUserService {
    AuthUser save(AuthUserDto authUserDto);
    TokenDto login(AuthUserDto authUserDto);
    TokenDto validate(String token);
}
