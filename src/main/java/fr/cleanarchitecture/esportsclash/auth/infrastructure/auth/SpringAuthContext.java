package fr.cleanarchitecture.esportsclash.auth.infrastructure.auth;

import fr.cleanarchitecture.esportsclash.auth.application.ports.AuthContext;
import fr.cleanarchitecture.esportsclash.auth.domain.model.AuthUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringAuthContext implements AuthContext {

    @Override
    public boolean isAuthenticated() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .isAuthenticated();
    }

    @Override
    public Optional<AuthUser> getUser() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication()
        ).map(authentication -> {
            if (authentication.getPrincipal() instanceof AuthUser) {
                return (AuthUser) authentication.getPrincipal();
            }

            return null;
        });
    }
}
