package fr.cleanarchitecture.esportsclash.team.usecases;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.application.usecases.CreateTeamCommand;
import fr.cleanarchitecture.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory.InMemoryTeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class CreateTeamTests {
    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private CreateTeamCommandHandler createTeamCommandHandler() {
        return new CreateTeamCommandHandler(teamRepository);
    };

    @Test
    public void shouldCreateTeam() {
        var createTeamCommand = new CreateTeamCommand("Team");
        var createTeamCommandHandler = createTeamCommandHandler();
        var response = createTeamCommandHandler.handle(createTeamCommand);
        var team = teamRepository.findById(response.getId());

        Assertions.assertTrue(team.isPresent());
        Assertions.assertEquals(team.get().getTeamName(), createTeamCommand.getTeamName());
    }

    @Test
    public void whenTeamAlreadyExists_shouldThrowException() {
        var team = new Team("123", "team", new HashSet<>());
        teamRepository.save(team);

        var createTeamCommand = new CreateTeamCommand("team");
        var createTeamCommandHandler = createTeamCommandHandler();
        var exception = Assertions.assertThrows(
                BadRequestException.class,
                () -> createTeamCommandHandler.handle(createTeamCommand)
        );
        Assertions.assertEquals("Team name already exists", exception.getMessage());
    }
}
