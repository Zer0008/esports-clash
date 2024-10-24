package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.PostgreSQLTestConfiguration;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.CreatePlayerDto;
import fr.cleanarchitecture.esportsclash.player.infrastructure.spring.PlayerConfiguration;
import org.junit.Assert;
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
@Import({PostgreSQLTestConfiguration.class, PlayerConfiguration.class})
public class CreatePlayerE2ETests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository inMemoryPlayerRepository;

    @Test
    public void shouldCreatePlayer() throws Exception {
        var dto = new CreatePlayerDto("fr/cleanarchitecture/esportsclash/player");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        var player = inMemoryPlayerRepository.findById(idResponse.getId());

        Assert.assertNotNull(player);
        Assert.assertEquals(dto.getName(), player.getName());
    }
}
