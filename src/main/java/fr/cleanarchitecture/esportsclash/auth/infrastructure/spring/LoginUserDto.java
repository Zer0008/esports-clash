package fr.cleanarchitecture.esportsclash.auth.infrastructure.spring;

public class LoginUserDto {
    private final String emailAddress;
    private final String password;

    public LoginUserDto(String emailAddress, String password) {
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
