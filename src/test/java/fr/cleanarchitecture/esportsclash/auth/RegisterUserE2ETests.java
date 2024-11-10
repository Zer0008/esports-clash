package fr.cleanarchitecture.esportsclash.auth;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.auth.infrastructure.spring.RegisterUserDto;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class RegisterUserE2ETests extends IntegrationTests {
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
