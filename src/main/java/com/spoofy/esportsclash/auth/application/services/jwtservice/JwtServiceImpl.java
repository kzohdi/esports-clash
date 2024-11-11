package com.spoofy.esportsclash.auth.application.services.jwtservice;

import com.spoofy.esportsclash.auth.domain.model.AuthUser;
import com.spoofy.esportsclash.auth.domain.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtServiceImpl implements JwtService {

    private final SecretKey key;

    private final JwtParser jwtParser;

    private final long expiration;

    private static final String EMAIL_ADDRESS_KEY = "emailAddress";

    public JwtServiceImpl(long expiration) {
        this.key = Jwts.SIG.HS256.key().build();
        this.jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();
        this.expiration = expiration;
    }

    @Override
    public String tokenize(User user) {
        var claims = Jwts.claims()
                .subject(user.getId())
                .add(EMAIL_ADDRESS_KEY, user.getEmailAddress())
                .build();

        var creationTime = LocalDateTime.now();
        var expirationTime = creationTime.plusSeconds(expiration);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(creationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();
    }

    @Override
    public AuthUser parse(String token) {
        var claims = jwtParser
                .parseSignedClaims(token)
                .getPayload();

        var id = claims.getSubject();
        var emailAddress = claims.get(EMAIL_ADDRESS_KEY, String.class);

        return new AuthUser(id, emailAddress);
    }
}
