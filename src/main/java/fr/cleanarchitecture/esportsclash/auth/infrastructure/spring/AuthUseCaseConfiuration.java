package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.JwtService;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.LoginUserCommandHandler;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthUseCaseConfiuration {

    @Bean
    public RegisterUserCommandHandler registerUserCommandHandler(UserRepository userRepository, PasswordHasher passwordHasher) {
        return new RegisterUserCommandHandler(userRepository, passwordHasher);
    }

    @Bean
    public LoginUserCommandHandler loginUserCommandHandler(UserRepository userRepository, JwtService jwtService, PasswordHasher passwordHasher) {
        return new LoginUserCommandHandler(userRepository, jwtService, passwordHasher);
    }
}
