package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthServicesConfiguration {
    @Bean
    public PasswordHasher passwordHasher() {
        return new BcryptPasswordHasher();
    }
}
