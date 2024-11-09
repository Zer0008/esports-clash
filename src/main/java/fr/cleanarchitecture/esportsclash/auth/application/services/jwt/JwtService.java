package fr.cleanarchitecture.esportsclash.auth.application.services.jwt;

import fr.cleanarchitecture.esportsclash.auth.domain.model.AuthUser;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;

public interface JwtService {
    String generateToken(User user);
    AuthUser getUserFromToken(String token);
}
