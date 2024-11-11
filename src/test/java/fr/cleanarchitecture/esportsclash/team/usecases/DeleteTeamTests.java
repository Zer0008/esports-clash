package fr.cleanarchitecture.esportsclash.team.usecases;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.application.usecases.DeleteTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.DeleteTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory.InMemoryTeamRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class DeleteTeamTests {
    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private DeleteTeamCommandHandler createCommandHandler() {
        return new DeleteTeamCommandHandler(teamRepository);
    };

    @Test
    public void shouldDeleteTeam() {
        var existingTeam = new Team("123", "Team", new HashSet<>());
        teamRepository.save(existingTeam);

        var deleteTeamCommand = new DeleteTeamCommand("123");
        var deleteTeamCommandHandler = createCommandHandler();
        deleteTeamCommandHandler.handle(deleteTeamCommand);

        var team = teamRepository.findById(existingTeam.getId());
        Assertions.assertTrue(team.isEmpty());
    }

    @Test
    public void whenTeamDoesNotExists_shouldThrowException() {
        var deleteTeamCommand = new DeleteTeamCommand("garbage");
        var deleteTeamCommandHandler = new DeleteTeamCommandHandler(teamRepository);

        Assert.assertThrows(
                "Team not found",
                NotFoundException.class,
                () -> deleteTeamCommandHandler.handle(deleteTeamCommand)
        );
    }
}
