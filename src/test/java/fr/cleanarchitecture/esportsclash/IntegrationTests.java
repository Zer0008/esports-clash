package fr.cleanarchitecture.esportsclash;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.jwt.JwtService;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.AuthAdapterConfiguration;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.AuthServicesConfiguration;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.PlayerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Import(
        {PostgreSQLTestConfiguration.class,
                PlayerConfiguration.class,
                AuthServicesConfiguration.class,
                AuthAdapterConfiguration.class}
)
public class IntegrationTests {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected JwtService jwtService;

    protected String createJwt() {
        var user = userRepository.findByEmailAddress("contact@cleanarchitecture.fr").orElse(null);
        if (user == null) {
            user = new User("123", "contact@cleanarchitecture.fr", "mot2passe");
        }
        userRepository.save(user);
        return "Bearer " + jwtService.generateToken(user);
    }

}
