package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommand;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommandHandler;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory.InMemoryUserRepository;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class RegisterCommandHandlerTest {

    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    private RegisterUserCommandHandler createRegisterHandler() {
        return new RegisterUserCommandHandler(userRepository, passwordHasher);
    }

    @BeforeEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    public void shouldRegisterUser() {
        var command = new RegisterUserCommand("contact@cleanarchitecture.fr", "mot2passe");
        var commandHandler = createRegisterHandler();

        var result = commandHandler.handle(command);
        var actualUser = userRepository.findById(result.getId()).get();

        Assert.assertEquals(command.getUserEmail(), actualUser.getUserEmailAddress());
        Assert.assertTrue(passwordHasher.match(command.getPassword(), actualUser.getPassword()));
    }

    @Test
    public void whenEmailAddressIsInUse_shouldThrowException() {
        var existingUser = new User("123", "contact@cleanarchitecture.fr", "mot2passe");
        userRepository.save(existingUser);

        var command = new RegisterUserCommand(existingUser.getUserEmailAddress(), "mot2passe");
        var commandHandler = createRegisterHandler();

        Assert.assertThrows(
                "Email address already in use",
                BadRequestException.class,
                () -> commandHandler.handle(command)
        );
    }
}
