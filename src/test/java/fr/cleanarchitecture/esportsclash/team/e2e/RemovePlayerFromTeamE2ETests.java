package fr.cleanarchitecture.esportsclash.team.e2e;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Role;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.spring.RemovePlayerFromTeamDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

@RunWith(SpringRunner.class)
public class RemovePlayerFromTeamE2ETests extends IntegrationTests {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    private final Team existingTeam = new Team("teamId", "teamName", new HashSet<>());
    private final Player existingPlayer = new Player("playerId", "playerName");

    @Before
    public void setUp() {
        teamRepository.clear();
        playerRepository.clear();
        teamRepository.save(existingTeam);
        playerRepository.save(existingPlayer);
    }

    @Test
    public void shouldRemovePlayerFromTeam() throws Exception {
        existingTeam.addMember(existingPlayer.getId(), Role.TOP);
        var dto = new RemovePlayerFromTeamDto("teamId", "playerId");

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/teams/remove-player-from-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var team = teamRepository.findById(dto.getTeamId()).get();
        Assert.assertFalse(team.hasMember(dto.getPlayerId(), Role.TOP));
    }

    @Test
    public void whenTeamDoesNotExist_shouldFail() throws Exception {
        var dto = new RemovePlayerFromTeamDto("teamId2", "playerId");

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/teams/remove-player-from-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        var dto = new RemovePlayerFromTeamDto("teamId", "playerId2");

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/teams/remove-player-from-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenPlayerNotInTeam_shouldFail() throws Exception {
        var player = new Player("playerId2", "playerName");
        existingTeam.addMember(player.getId(), Role.TOP);
        var dto = new RemovePlayerFromTeamDto("teamId", "playerId");

        mockMvc
                .perform(MockMvcRequestBuilders.delete("/teams/remove-player-from-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
