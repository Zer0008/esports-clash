package fr.cleanarchitecture.esportsclash.team.e2e;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.infrastructure.spring.CreateTeamDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class CreateTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldCreateTeam() throws Exception {
        var dto = new CreateTeamDto("team");

        var result = mockMvc
                .perform(MockMvcRequestBuilders.post("/teams")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        var idResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                IdResponse.class);

        var team = teamRepository.findById(idResponse.getId());

        Assert.assertTrue(team.isPresent());
        Assert.assertEquals(dto.getName(), team.get().getTeamName());
    }
}
