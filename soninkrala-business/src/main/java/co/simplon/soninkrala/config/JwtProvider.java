package co.simplon.soninkrala.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;

public class JwtProvider {

    private final Algorithm algorithm;
    private final long expiration;
    private final String issuer;

    public JwtProvider(Algorithm algorithm, long expiration, String issuer) {
        this.algorithm = algorithm;
        this.expiration = expiration;
        this.issuer = issuer;
    }

    public String generateToken(String subject) {
        Instant instant = Instant.now();
        JWTCreator.Builder builder = JWT.create().withIssuer(issuer).withSubject(subject);
        if (expiration > -1) {
            Instant expiresAt = instant.plusSeconds(expiration);
            builder.withExpiresAt(expiresAt);
        }
        return builder.sign(algorithm);
    }
}

