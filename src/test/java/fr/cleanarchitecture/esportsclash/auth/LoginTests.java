package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.ConcreteJwtService;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.JwtService;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.LoginUserCommand;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.LoginUserCommandHandler;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory.InMemoryUserRepository;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LoginTests {

    private final UserRepository userRepository = new InMemoryUserRepository();
    private final JwtService jwtService = new ConcreteJwtService();
    private final User user = new User("123",
                                     "contact@cleanarchitecture.fr",
                                     new BcryptPasswordHasher().hash("mot2passe"));

    private LoginUserCommandHandler createHandler() {
        return new LoginUserCommandHandler(userRepository, jwtService, new BcryptPasswordHasher());
    }

    @BeforeEach
    public void setUp() {
        userRepository.clear();
        userRepository.save(user);
    }

    // Mot de passe et adresse email correct
    @Nested
    class HappyPath {

        @Test
        public void shouldReturnTheUser() {
            var loginUserCommand = new LoginUserCommand("contact@cleanarchitecture.fr", "mot2passe");
            var loginUserCommandHandler = createHandler();
            var result = loginUserCommandHandler.handle(loginUserCommand);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(user.getIdUser(), result.getId());
            Assertions.assertEquals(user.getUserEmailAddress(), result.getEmail());

            var authenticatedUser = jwtService.getUserFromToken(result.getToken());

            Assertions.assertNotNull(authenticatedUser);
            Assertions.assertEquals(user.getIdUser(), authenticatedUser.getId());
            Assertions.assertEquals(user.getUserEmailAddress(), authenticatedUser.getEmailAddress());
        }
    }

    // Adresse email incorrect
    @Nested
    class EmailAddressIncorrect {

        @Test
        public void shouldThrowExceptionWhenEmailAddressIsIncorrect() {
            var loginUserCommand = new LoginUserCommand("arnaud@cleanarchitecture.fr", "mot2passe");
            var loginUserCommandHandler = createHandler();

            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> loginUserCommandHandler.handle(loginUserCommand)
            );
        }
    }

    // Mot de passe incorrect
    @Nested
    class PasswordIncorrect {

        @Test
        public void shouldThrowExceptionWhenPasswordIsIncorrect() {
            var loginUserCommand = new LoginUserCommand("contact@cleanarchitecture.fr", "mot2passeIncorrect");
            var loginUserCommandHandler = createHandler();

            var exception = Assertions.assertThrows(
                    BadRequestException.class,
                    () -> loginUserCommandHandler.handle(loginUserCommand)
            );

            Assertions.assertEquals(
                    exception.getMessage(),
                    "Invalid password"
            );
        }
    }
}
