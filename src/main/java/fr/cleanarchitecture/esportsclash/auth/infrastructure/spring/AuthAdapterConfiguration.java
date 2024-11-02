package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdapterConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }
}
