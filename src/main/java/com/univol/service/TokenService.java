package com.univol.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.univol.dto.Token;
import com.univol.model.RoleUsuario;
import com.univol.model.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private Instant expiresAt = LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.ofHours(-3));
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    public Token createToken(Usuario user){
        var jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new Token(jwt, user.getEmail());


    }

    public Usuario getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        String role = jwtVerified.getClaim("role").asString();
        return Usuario.builder()
                .id(Long.valueOf(jwtVerified.getSubject()))
                .email(jwtVerified.getClaim("email").toString())
                .role(RoleUsuario.valueOf(role))
                .build();
    }
}
