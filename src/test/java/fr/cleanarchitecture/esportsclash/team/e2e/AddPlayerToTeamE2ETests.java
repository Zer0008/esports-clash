package fr.cleanarchitecture.esportsclash.team.e2e;

import fr.cleanarchitecture.esportsclash.IntegrationTests;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Role;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.spring.AddPlayerToTeamDto;
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
public class AddPlayerToTeamE2ETests extends IntegrationTests {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

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
    public void shouldAddPlayerToTeam() throws Exception {
        var dto = new AddPlayerToTeamDto("teamId", "playerId", Role.TOP);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var team = teamRepository.findById(dto.getTeamId()).get();
        Assert.assertTrue(team.hasMember(dto.getPlayerId(), dto.getRole()));
    }

    @Test
    public void whenPlayerIsAlreadyInTeam_shouldFail() throws Exception {
        existingTeam.addMember(existingPlayer.getId(), Role.TOP);
        var dto = new AddPlayerToTeamDto("teamId", "playerId", Role.TOP);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenRolePlayerIsAlreadyTakenInTheTeam_shouldFail() throws Exception {
        existingTeam.addMember(existingPlayer.getId(), Role.TOP);
        playerRepository.save(new Player("playerId2", "playerName2"));
        var dto = new AddPlayerToTeamDto("teamId", "playerId2", Role.TOP);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenTeamDoesNotExist_shouldFail() throws Exception {
        var dto = new AddPlayerToTeamDto("teamId2", "playerId", Role.TOP);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() throws Exception {
        var dto = new AddPlayerToTeamDto("teamId", "playerId2", Role.TOP);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/teams/add-player-to-team")
                        .header("Authorization", createJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
