package com.spoofy.esportsclash.auth.application.services.jwtservice;

import com.spoofy.esportsclash.auth.domain.models.AuthUser;
import com.spoofy.esportsclash.auth.domain.models.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConcreteJwtService implements JwtService {

    private final long expiration;
    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private final JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

    public ConcreteJwtService(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String tokenize(User user) {
        var claims = Jwts.claims()
                .subject(user.getId())
                .add("emailAddress", user.getEmailAddress())
                .build();

        var createdAt = LocalDateTime.now();
        var expiresAt = createdAt.plusSeconds(expiration);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public AuthUser decode(String token) {
        var claims = jwtParser.parseSignedClaims(token).getPayload();

        return new AuthUser(
                claims.getSubject(),
                claims.get("emailAddress", String.class)
        );
    }
}
