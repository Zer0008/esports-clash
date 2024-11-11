package fr.cleanarchitecture.esportsclash.team.e2e;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

@RunWith(SpringRunner.class)
public class DeleteTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldDeleteTeam() throws Exception {
        var existingTeam = new Team("123", "team", new HashSet<>());
        teamRepository.save(existingTeam);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/teams/123")
                        .header("Authorization", createJwt()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var team = teamRepository.findById(existingTeam.getId());

        Assert.assertTrue(team.isEmpty());
    }

    @Test
    public void whenTeamDoesNotExist_shouldFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/teams/garbage")
                        .header("Authorization", createJwt())
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
