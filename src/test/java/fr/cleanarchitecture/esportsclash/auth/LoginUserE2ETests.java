package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.PostgreSQLTestConfiguration;
import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.application.services.passwordhasher.PasswordHasher;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.domain.viewmodel.LoggedInUserViewModel;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.AuthAdapterConfiguration;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.AuthServicesConfiguration;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.LoginUserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@Import({PostgreSQLTestConfiguration.class, AuthAdapterConfiguration.class, AuthServicesConfiguration.class})
public class LoginUserE2ETests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;

    private User user;

    @Before
    public void setUp() {
        userRepository.clear();
        this.user = new User(
                "123",
                "contact@cleanarchitecture.fr",
                passwordHasher.hash("mot2passe"));
        userRepository.save(this.user);
    }

    @Test
    public void shouldLoginUser() throws Exception {
        var dto = new LoginUserDto("contact@cleanarchitecture.fr", "mot2passe");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var loggedUser = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                LoggedInUserViewModel.class);

        var utilisateur = userRepository.findById(loggedUser.getId()).get();

        Assert.assertNotNull("123", utilisateur.getId());
        Assert.assertEquals(dto.getEmailAddress(), utilisateur.getUserEmailAddress());
    }

    @Test
    public void whenEmailAddressIsIncorrect_shouldThrowException() throws Exception {
        var dto = new LoginUserDto("incorrect@cleanarchitecture.fr", "mot2passe");

        mockMvc
            .perform(MockMvcRequestBuilders.post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenInvalidPassword_shouldThrowException() throws Exception {
        var dto = new LoginUserDto("contact@cleanarchitecture.fr", "mot2passeInvalid");

        mockMvc
            .perform(MockMvcRequestBuilders.post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
