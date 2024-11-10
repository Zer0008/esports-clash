package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.JwtService;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;

public class LoginUserCommandHandler implements Command.Handler<LoginUserCommand, LoggedInUserViewModel> {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;

    public LoginUserCommandHandler(UserRepository userRepository, JwtService jwtService, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public LoggedInUserViewModel handle(LoginUserCommand loginUserCommand) {
        var user = this.userRepository.findByEmailAddress(loginUserCommand.getEmailAddress()).orElseThrow(
                () -> new NotFoundException("User with email address " + loginUserCommand.getEmailAddress() + " not found.")
        );

        var matchPassword = this.passwordHasher.match(loginUserCommand.getPassword(), user.getPassword());

        if (!matchPassword) {
            throw new BadRequestException("Invalid password");
        }

        var authenticateToken = this.jwtService.generateToken(user);

        return new LoggedInUserViewModel(user.getId(), user.getUserEmailAddress(), authenticateToken);
    }
}
