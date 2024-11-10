package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.CreatePlayerDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class CreatePlayerE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository inMemoryPlayerRepository;

    @Test
    public void shouldCreatePlayer() throws Exception {
        var dto = new CreatePlayerDto("player");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/players")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        var player = inMemoryPlayerRepository.findById(idResponse.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());
    }
}
