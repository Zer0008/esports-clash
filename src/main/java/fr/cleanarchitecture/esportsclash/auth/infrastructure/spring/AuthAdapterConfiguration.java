package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.auth.application.ports.AuthContext;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.auth.SpringAuthContext;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.jpa.SQLUserDataAccessor;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.jpa.SQLUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdapterConfiguration {

    @Bean
    public UserRepository userRepository(SQLUserDataAccessor userDataAccessor) {
        return new SQLUserRepository(userDataAccessor);
    }

    @Bean
    public AuthContext authContext() {
        return new SpringAuthContext();
    }
}
