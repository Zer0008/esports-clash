package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.PostgreSQLTestConfiguration;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.AuthAdapterConfiguration;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.RegisterUserDto;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({PostgreSQLTestConfiguration.class, AuthAdapterConfiguration.class})
public class RegisterUserE2ETests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.clear();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        var dto = new RegisterUserDto("contact@hexagonalarchitecture.fr", "mot2passe");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        var user = userRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(user);
        Assert.assertEquals(dto.getEmailAddress(), user.getUserEmailAddress());
    }

    @Test
    public void whenEmailAddressAlreadyInUse_throwException() throws Exception {
        var existingUser = new User("123", "contact@cleanarchitecture.fr", "mot2passe");
        userRepository.save(existingUser);

        var dto = new RegisterUserDto("contact@cleanarchitecture.fr", "mot2passe");
        mockMvc
            .perform(MockMvcRequestBuilders.post("/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
