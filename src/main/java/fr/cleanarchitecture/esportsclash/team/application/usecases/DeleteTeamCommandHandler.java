package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;

public class DeleteTeamCommandHandler implements Command.Handler<DeleteTeamCommand, Void> {
    private final TeamRepository teamRepository;

    public DeleteTeamCommandHandler(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public Void handle(DeleteTeamCommand deleteTeamCommand) {
        var teamToDelete = this.teamRepository.findById(deleteTeamCommand.getTeamId())
                .orElseThrow(() -> new NotFoundException("Team not found"));
        this.teamRepository.delete(teamToDelete);
        return null;
    }
}
