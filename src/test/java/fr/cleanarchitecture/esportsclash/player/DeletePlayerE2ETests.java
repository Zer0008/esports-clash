package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.PostgreSQLTestConfiguration;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.PlayerConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Import({PostgreSQLTestConfiguration.class, PlayerConfiguration.class})
public class DeletePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldDeletePlayer() throws Exception {
        var existingPlayer = new Player("123", "player");
        playerRepository.savePlayer(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders.delete("/players/123"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var player = playerRepository.findById(existingPlayer.getId());

        Assert.assertTrue(player.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/players/garbage"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
