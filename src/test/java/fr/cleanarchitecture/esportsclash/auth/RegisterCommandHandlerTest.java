package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommandHandler;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.ResgiterUserCommand;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory.InMemoryUserRepository;
import org.junit.Assert;
import org.junit.Test;

public class RegisterCommandHandlerTest {

    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    private RegisterUserCommandHandler createRegisterHandler() {
        return new RegisterUserCommandHandler(userRepository, passwordHasher);
    }

    @Test
    public void shouldRegisterUser() {
        var command = new ResgiterUserCommand("contact@cleanarchitecture.fr", "mot2passe");
        var commandHandler = createRegisterHandler();

        var result = commandHandler.handle(command);
        var actualUser = userRepository.findById(result.getId()).get();

        Assert.assertEquals(command.getUserEmail(), actualUser.getUserEmailAddress());
        Assert.assertTrue(passwordHasher.match(command.getPassword(), actualUser.getPassword()));
    }
}
