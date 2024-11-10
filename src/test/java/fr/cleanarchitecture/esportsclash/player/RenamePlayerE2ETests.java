package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.RenamePlayerDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class RenamePlayerE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldRenamePlayer() throws Exception {
        var jwt = createJwt();
        var dto = new RenamePlayerDto("playerRenamed");
        var existingPlayer = new Player("123", "player");
        playerRepository.save(existingPlayer);

        mockMvc.perform(MockMvcRequestBuilders.patch("/players/123/name")
                        .header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var player = playerRepository.findById(existingPlayer.getId()).get();

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        var jwt = createJwt();
        var dto = new RenamePlayerDto("playerRenamed");
        mockMvc.perform(MockMvcRequestBuilders.patch("/players/garbage/name")
                        .header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
