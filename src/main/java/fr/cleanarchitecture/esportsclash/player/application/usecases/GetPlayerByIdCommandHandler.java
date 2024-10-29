package fr.cleanarchitecture.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommandHandler implements Command.Handler<GetPlayerByIdCommand, PlayerViewModel> {
    private final PlayerRepository playerRepository;

    public GetPlayerByIdCommandHandler(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerViewModel handle(GetPlayerByIdCommand getPlayerByIdCommand) {
        var player = playerRepository.findById(getPlayerByIdCommand.getPlayerId())
                .orElseThrow(() -> new NotFoundException("Player not found"));

        return new PlayerViewModel(player.getId(), player.getName());
    }
}
