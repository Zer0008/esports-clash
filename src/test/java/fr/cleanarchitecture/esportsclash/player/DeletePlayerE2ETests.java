package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class DeletePlayerE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldDeletePlayer() throws Exception {
        var jwt = createJwt();
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/123")
                        .header("Authorization", jwt))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var player = playerRepository.findById(existingPlayer.getId());

        Assert.assertTrue(player.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/garbage")
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
