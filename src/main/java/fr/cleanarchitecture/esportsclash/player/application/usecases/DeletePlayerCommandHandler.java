package fr.cleanarchitecture.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;

public class DeletePlayerCommandHandler implements Command.Handler<DeletePlayerCommand, Void> {

    private final PlayerRepository playerRepository;

    public DeletePlayerCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(DeletePlayerCommand deletePlayerCommand) {
        var playerToDelete = playerRepository.findById(deletePlayerCommand.getId()).orElseThrow(
                () -> new NotFoundException("Player not found")
        );
        playerRepository.delete(playerToDelete);
        return null;
    }
}
