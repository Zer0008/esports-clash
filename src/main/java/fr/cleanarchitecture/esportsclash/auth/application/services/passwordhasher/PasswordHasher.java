package fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher;

public interface PasswordHasher {
    String hash(String password);
    Boolean match(String password, String hashedPassword);
}
