package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.ConcreteJwtService;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.JwtService;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import org.junit.Assert;
import org.junit.Test;

public class JwtServiceTest {

    @Test
    public void shouldTokenizeUser() {
        JwtService jwtService = new ConcreteJwtService();
        User user = new User("123",
                "contact@cleanarchitecture.fr",
                "mot2passe");

        var token = jwtService.generateToken(user);
        var authUser = jwtService.getUserFromToken(token);

        Assert.assertNotNull(authUser);
        Assert.assertEquals(authUser.getId(), user.getId());
        Assert.assertEquals(authUser.getEmailAddress(), user.getUserEmailAddress());
    }
}
