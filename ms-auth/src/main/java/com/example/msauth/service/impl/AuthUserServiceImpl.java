package com.example.msauth.service.impl;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;
import com.example.msauth.repository.AuthUserRepository;
import com.example.msauth.security.JwtProvider;
import com.example.msauth.service.AuthUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository,
                               PasswordEncoder passwordEncoder,
                               JwtProvider jwtProvider) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthUser save(AuthUserDto authUserDto) {
        // Verificar si el usuario ya existe
        if (authUserRepository.findByUserName(authUserDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        // Cifrar la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(authUserDto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .userName(authUserDto.getUserName())
                .password(encodedPassword)
                .build();

        return authUserRepository.save(authUser);
    }

    @Override
    public TokenDto login(AuthUserDto authUserDto) {
        Optional<AuthUser> userOpt = authUserRepository.findByUserName(authUserDto.getUserName());

        // Validar si el usuario existe
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        AuthUser user = userOpt.get();

        // Verificar la contraseña
        if (!passwordEncoder.matches(authUserDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        // Generar el token JWT
        String token = jwtProvider.createToken(user);
        return new TokenDto(token);
    }

    @Override
    public TokenDto validate(String token) {
        // Validar si el token es válido
        if (!jwtProvider.validate(token)) {
            throw new IllegalArgumentException("Token inválido");
        }

        // Extraer el nombre de usuario del token
        String username = jwtProvider.getUserNameFromToken(token);
        if (authUserRepository.findByUserName(username).isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado para el token proporcionado");
        }

        return new TokenDto(token);
    }
}
