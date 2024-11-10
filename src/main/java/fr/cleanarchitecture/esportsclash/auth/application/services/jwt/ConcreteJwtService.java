package fr.cleanarchitecture.esportsclash.auth.application.services.jwt;

import fr.cleanarchitecture.esportsclash.auth.domain.model.AuthUser;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ConcreteJwtService implements JwtService {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    private final Long expirationDate;

    public ConcreteJwtService() {
        var secret = "super_secret_please_do_not_share_it";
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
        this.expirationDate = 60L;
    }

    @Override
    public String generateToken(User user) {
        var claims = Jwts
                .claims()
                .subject(user.getId())
                .add("emailAdress", user.getUserEmailAddress())
                .build();

        var createdAt = LocalDateTime.now();
        var expiredAt = createdAt.plusSeconds(this.expirationDate);

        return Jwts
                .builder()
                .claims(claims)
                .issuedAt(
                        Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .expiration(
                        Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant())
                )
                .signWith(secretKey)
                .compact();
    }

    @Override
    public AuthUser getUserFromToken(String token) {
        var claims = jwtParser.parseSignedClaims(token).getPayload();
        var id = claims.getSubject();
        var emailAdress = claims.get("emailAdress", String.class);

        return new AuthUser(id, emailAdress);
    }
}
