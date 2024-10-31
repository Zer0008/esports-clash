package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;

public class RegisterUserCommandHandler implements Command.Handler<ResgiterUserCommand, IdResponse> {

    private final UserRepository userRepository;

    public RegisterUserCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public IdResponse handle(ResgiterUserCommand resgiterUserCommand) {
        var user = new User(resgiterUserCommand.getUserEmail(), resgiterUserCommand.getPassword());
        userRepository.save(user);
        return new IdResponse(user.getId());
    }
}
