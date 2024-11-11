package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;

public class AddPlayerToTeamCommandHandler implements Command.Handler<AddPlayerToTeamCommand, Void> {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public AddPlayerToTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(AddPlayerToTeamCommand addPlayerToTeamCommand) {
        var teamQuery = this.teamRepository.findById(addPlayerToTeamCommand.getTeamId()).orElseThrow(
                () -> new NotFoundException("Team id not found")
        );
        var playerQuery = this.playerRepository.findById(addPlayerToTeamCommand.getPlayerId()).orElseThrow(
                () -> new NotFoundException("Player id not found")
        );
        teamQuery.addMember(playerQuery.getId(), addPlayerToTeamCommand.getRole());
        return null;
    }
}
