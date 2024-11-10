package fr.cleanarchitecture.esportsclash.auth.domain.model;

import fr.cleanarchitecture.esportsclash.core.domain.model.BaseEntity;

public class User extends BaseEntity {
    private final String userEmailAddress;
    private final String password;

    public User(String id, String userEmailAddress, String password) {
        this.id = id;
        this.userEmailAddress = userEmailAddress;
        this.password = password;
    }

    public String getIdUser() {
        return id;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public String getPassword() {
        return password;
    }
}
