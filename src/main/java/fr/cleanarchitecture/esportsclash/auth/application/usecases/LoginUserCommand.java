package fr.cleanarchitecture.esportsclash.auth.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;

public class LoginUserCommand implements Command<LoggedInUserViewModel> {

    private String emailAddress;
    private String password;

    public LoginUserCommand() {}

    public LoginUserCommand(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
