package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.usecases.RegisterUserCommandHandler;
import fr.cleanarchitecture.esportsclash.auth.application.usecases.ResgiterUserCommand;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory.InMemoryUserRepository;
import org.junit.Assert;
import org.junit.Test;

public class RegisterCommandHandlerTest {

    @Test
    public void shouldRegisterUser() {
        var userRepository = new InMemoryUserRepository();
        var command = new ResgiterUserCommand("contact@cleanarchitecture.fr", "mot2passe");
        var commandHandler = new RegisterUserCommandHandler(userRepository);

        var result = commandHandler.handle(command);
        var actualUser = userRepository.findById(result.getId()).get();

        Assert.assertEquals(command.getUserEmail(), actualUser.getUserEmailAddress());
        Assert.assertEquals(command.getPassword(), actualUser.getPassword());
    }
}
