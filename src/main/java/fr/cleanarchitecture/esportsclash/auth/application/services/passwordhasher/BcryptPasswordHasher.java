package fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordHasher implements PasswordHasher {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public Boolean match(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
