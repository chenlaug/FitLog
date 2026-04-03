package com.example.FitLog.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    // Lit le secret et l'expiration depuis application.properties
    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    // Crée un token JWT avec l'UUID et l'email de l'utilisateur
    public String generateToken(UUID userId, String email) {
        return Jwts.builder()
                .subject(userId.toString())  // l'UUID est mis comme identifiant principal
                .claim("email", email)       // l'email est ajouté comme donnée extra
                .issuedAt(new Date())        // date de création
                .expiration(new Date(System.currentTimeMillis() + expiration)) // date d'expiration
                .signWith(secretKey)         // signature avec la clé secrète
                .compact();
    }

    // Récupère l'UUID depuis le token
    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaim(token, Claims::getSubject));
    }

    // Récupère l'email depuis le token
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    // Méthode générique : ouvre le token et retourne ce que tu lui demandes
    // Pour ajouter un nouveau claim à extraire, crée une méthode qui appelle celle-ci
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)       // vérifie que le token est signé avec notre clé
                .build()
                .parseSignedClaims(token)    // décode le token
                .getPayload();               // récupère les données à l'intérieur
        return claimsResolver.apply(claims); // retourne ce qui a été demandé
    }

    // Vérifie si le token est valide (bien signé et non expiré)
    public boolean isTokenValid(String token) {
        try {
            extractUserId(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
