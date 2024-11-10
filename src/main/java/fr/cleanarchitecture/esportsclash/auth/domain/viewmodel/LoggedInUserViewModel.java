package fr.cleanarchitecture.esportsclash.auth.domain.viewmodel;

public class LoggedInUserViewModel {

    private String id;
    private String email;
    private String token;

    public LoggedInUserViewModel() {}

    public LoggedInUserViewModel(String id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
