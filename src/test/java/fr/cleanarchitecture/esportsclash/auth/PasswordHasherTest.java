package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.BcryptPasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import org.junit.Assert;
import org.junit.Test;

public class PasswordHasherTest {

    private PasswordHasher createPasswordHasher() {
        return new BcryptPasswordHasher();
    }

    @Test
    public void shouldHashPassword() {
        var passwordHasher = createPasswordHasher();
        String clearPassword = "password";

        String hashedPassword = passwordHasher.hash(clearPassword);

        Assert.assertTrue(passwordHasher.match(clearPassword, hashedPassword));
    }
}
