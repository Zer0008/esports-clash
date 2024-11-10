package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.PlayerViewModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class GetPlayerByIdE2ETests extends IntegrationTests {
    @Autowired
    protected PlayerRepository playerRepository;

    @Test
    public void shouldGetPlayerById() throws Exception {
        var jwtToken = createJwt();
        var player = new Player("123", "player");
        playerRepository.save(player);

        var result = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/players/{id}", player.getId())
                                .header("Authorization", jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var playerViewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class);

        Assert.assertNotNull(playerViewModel);
        Assert.assertEquals(playerViewModel.getName(), player.getName());
    }

    @Test
    public void whenPlayerNotExists_shouldThrowException() throws Exception {
        var jwtToken = createJwt();
        var player = new Player("123", "player");
        playerRepository.save(player);

        mockMvc
            .perform(
                    MockMvcRequestBuilders.get("/players/{id}", "111")
                            .header("Authorization", jwtToken))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
