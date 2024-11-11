package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.BadRequestException;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;

import java.util.HashSet;
import java.util.UUID;

public class CreateTeamCommandHandler implements Command.Handler<CreateTeamCommand, IdResponse> {

    private final TeamRepository teamRepository;

    public CreateTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public IdResponse handle(CreateTeamCommand createTeamCommand) {
        var teamQuery = teamRepository.findByName(createTeamCommand.getTeamName());
        if (teamQuery.isPresent()) {
            throw new BadRequestException("Team name already exists");
        }
        var team = new Team(
                UUID.randomUUID().toString(),
                createTeamCommand.getTeamName(),
                new HashSet<>()
        );
        teamRepository.save(team);
        return new IdResponse(team.getId());
    }
}
