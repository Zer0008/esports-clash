package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.PostgreSQLTestConfiguration;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.PlayerViewModel;
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
public class getPlayerByIdE2ETests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository inMemoryPlayerRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void shouldGetPlayerById() throws Exception {
        var player = new Player("123", "player");
        playerRepository.save(player);

        var result = mockMvc
                .perform(MockMvcRequestBuilders.get("/players/{id}", player.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var playerViewModel = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PlayerViewModel.class);

        Assert.assertNotNull(playerViewModel);
        Assert.assertEquals(playerViewModel.getName(), player.getName());
    }
}
