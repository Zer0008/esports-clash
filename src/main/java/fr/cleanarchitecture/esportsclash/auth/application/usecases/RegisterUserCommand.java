package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;

public class RegisterUserCommand implements Command<IdResponse> {

    private final String userEmail;
    private final String password;

    public RegisterUserCommand(String userEmail, String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }
}
