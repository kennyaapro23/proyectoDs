package com.example.msempresa.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    /**
     * Decodifica el token JWT y extrae el userId.
     *
     * @param token El token JWT recibido en el encabezado Authorization.
     * @return El userId extraído del token, o null si no se puede decodificar.
     */
    public Integer extractUserIdFromToken(String token) {
        try {
            // Remover el prefijo "Bearer " si está presente
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Decodificar el token sin validar la firma
            Claims claims = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Extraer el ID del usuario desde las claims
            return claims.get("id", Integer.class);
        } catch (Exception e) {
            // Registrar el error y retornar null
            System.err.println("Error al decodificar el token: " + e.getMessage());
            return null;
        }
    }
}
