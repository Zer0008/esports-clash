package fr.cleanarchitecture.esportsclash.auth.application.ports;

import fr.cleanarchitecture.esportsclash.auth.domain.model.AuthUser;

import java.util.Optional;

public interface AuthContext {
    boolean isAuthenticated();
    Optional<AuthUser> getUser();
}
