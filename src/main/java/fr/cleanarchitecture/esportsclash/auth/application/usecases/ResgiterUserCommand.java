package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;

public class ResgiterUserCommand implements Command<IdResponse> {

    private final String userEmail;
    private final String password;

    public ResgiterUserCommand(String userEmail, String password) {
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
