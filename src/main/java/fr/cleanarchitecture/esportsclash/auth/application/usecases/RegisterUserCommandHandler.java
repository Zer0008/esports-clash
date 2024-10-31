package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;

public class RegisterUserCommandHandler implements Command.Handler<ResgiterUserCommand, IdResponse> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterUserCommandHandler(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public IdResponse handle(ResgiterUserCommand resgiterUserCommand) {
        var userEmailAddressAlreadyInUse = userRepository.emailAddressAvailable(resgiterUserCommand.getUserEmail());
        if (!userEmailAddressAlreadyInUse) {
            throw new IllegalArgumentException("Email address already in use");
        }

        var user = new User(
                resgiterUserCommand.getUserEmail(),
                passwordHasher.hash(resgiterUserCommand.getPassword()));

        userRepository.save(user);
        return new IdResponse(user.getId());
    }
}
