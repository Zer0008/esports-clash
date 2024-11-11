package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;

public class RemovePlayerFromTeamCommandHandler implements Command.Handler<RemovePlayerFromTeamCommand, Void> {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public RemovePlayerFromTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(RemovePlayerFromTeamCommand removePlayerFromTeamCommand) {
        var team = teamRepository.findById(removePlayerFromTeamCommand.getTeamId()).orElseThrow(
                () -> new NotFoundException("Team id not found")
        );
        var player = playerRepository.findById(removePlayerFromTeamCommand.getPlayerId()).orElseThrow(
                () -> new NotFoundException("Player id not found")
        );

        team.removeMember(player.getId());
        teamRepository.save(team);
        return null;
    }
}
