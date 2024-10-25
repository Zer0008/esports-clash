package fr.cleanarchitecture.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;

public class RenamePlayerCommandHandler implements Command.Handler<RenamePlayerCommand, Void> {

    private final PlayerRepository playerRepository;

    public RenamePlayerCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Void handle(RenamePlayerCommand command) {
        var existingPlayer = playerRepository.findById(command.getId());

        if (existingPlayer.isEmpty()) {
            throw new NotFoundException("Player with not found");
        }

        var player = existingPlayer.get();
        player.renamePlayer(command.getName());
        playerRepository.save(player);
        return null;
    }
}
