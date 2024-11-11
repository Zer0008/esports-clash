package fr.cleanarchitecture.esportsclash.team.usecases;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.application.usecases.AddPlayerToTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.domain.model.Role;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;


public class AddPlayerToTeamTests {
    private final TeamRepository teamRepository = new InMemoryTeamRepository();
    private final PlayerRepository playerRepository = new InMemoryPlayerRepository();

    private final Team team = new Team("123", "teamName", new HashSet<>());
    private final Player player = new Player("player123", "playerName");

    private AddPlayerToTeamCommandHandler createCommandHandler() {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @Before
    public void setUp() {
        teamRepository.clear();
        playerRepository.clear();
        teamRepository.save(team);
        playerRepository.save(player);
    }

    @Test
    public void shouldAddMemberToTeam() {
        var addPlayerToTeamCommand = new AddPlayerToTeamCommand(player.getId(), team.getId(), Role.TOP);
        var addPlayerToTeamCommandHandler = createCommandHandler();
        addPlayerToTeamCommandHandler.handle(addPlayerToTeamCommand);

        Assert.assertTrue(team.hasMember(player.getId(), Role.TOP));
        Assert.assertEquals(1, team.getMembers().size());
    }

    @Test
    public void whenPlayerIsAlreadyInTeam_shouldFail() {
        team.addMember(player.getId(), Role.TOP);

        var command = new AddPlayerToTeamCommand("player123", team.getId(), Role.TOP);
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                BadRequestException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Player player123 is already in the team");
    }

    @Test
    public void whenRolePlayerIsAlreadyTakenInTheTeam_shouldFail() {
        var playerToAdd = new Player("player124", "playerName2");
        playerRepository.save(playerToAdd);
        team.addMember(player.getId(), Role.TOP);

        var command = new AddPlayerToTeamCommand(playerToAdd.getId(), team.getId(), Role.TOP);
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                BadRequestException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Role TOP is already taken in the team");
    }

    @Test
    public void whenTeamDoesNotExist_shouldFail() {
        var command = new AddPlayerToTeamCommand(player.getId(), "teamId", Role.TOP);
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Team id not found");
    }

    @Test
    public void whenPlayerDoesNotExist_shouldFail() {
        var command = new AddPlayerToTeamCommand("playerId", team.getId(), Role.TOP);
        var commandHandler = createCommandHandler();

        var exception = Assert.assertThrows(
                NotFoundException.class,
                () -> commandHandler.handle(command)
        );
        Assert.assertEquals(exception.getMessage(), "Player id not found");
    }
}
