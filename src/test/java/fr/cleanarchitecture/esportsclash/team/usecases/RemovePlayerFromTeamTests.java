package fr.cleanarchitecture.esportsclash.team.usecases;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.application.usecases.RemovePlayerFromTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.domain.model.Role;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

public class RemovePlayerFromTeamTests {

    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();
    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final Team team = new Team("team123", "TeamName", new HashSet<>());
    private final Player player = new Player("player123", "playerName");

    private RemovePlayerFromTeamCommandHandler createCommandHandler() {
        return new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);
    }

    @Before
    public void setUp() {
        teamRepository.clear();
        playerRepository.clear();
        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    public void shouldRemovePlayerFromTeam() {
        this.team.addMember(this.player.getId(), Role.TOP);
        var command = new RemovePlayerFromTeamCommand(this.team.getId(), this.player.getId());
        var commandHandler = createCommandHandler();

        commandHandler.handle(command);

        Assert.assertFalse(this.team.hasMember(this.player.getId(), Role.TOP));
    }

    @Test
    public void whenTeamDoesNotExist_shouldFail() {
        var command = new RemovePlayerFromTeamCommand("teamId", player.getId());
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Team id not found");
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() {
        var command = new RemovePlayerFromTeamCommand(team.getTeamId(), "playerId");
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Player id not found");
    }

    @Test
    public void whenPlayerNotInTeam_shouldFail() {
        var command = new RemovePlayerFromTeamCommand(team.getTeamId(), player.getId());
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                BadRequestException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Player player123 is not in the team");
    }
}
