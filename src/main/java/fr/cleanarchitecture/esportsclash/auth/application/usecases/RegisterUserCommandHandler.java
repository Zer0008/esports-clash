package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class RegisterUserCommandHandler implements Command.Handler<RegisterUserCommand, IdResponse> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterUserCommandHandler(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public IdResponse handle(RegisterUserCommand registerUserCommand) {
        var userEmailAddressAlreadyInUse = userRepository.findByEmailAddress(registerUserCommand.getUserEmail());
        if (userEmailAddressAlreadyInUse.isPresent()) {
            throw new BadRequestException("Email address already in use");
        }

        var user = new User(
                UUID.randomUUID().toString(),
                registerUserCommand.getUserEmail(),
                passwordHasher.hash(registerUserCommand.getPassword()));

        userRepository.save(user);
        return new IdResponse(user.getId());
    }
}
