package fr.cleanarchitecture.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;

import java.util.UUID;

public class CreatePlayerCommandHandler implements Command.Handler<CreatePlayerCommand, IdResponse> {

    private final PlayerRepository inMemoryPlayerRepository;

    public CreatePlayerCommandHandler(PlayerRepository inMemoryPlayerRepository) {
        this.inMemoryPlayerRepository = inMemoryPlayerRepository;
    }

    public IdResponse handle(CreatePlayerCommand command) {
        var player = new Player(UUID.randomUUID().toString(), command.getName());
        inMemoryPlayerRepository.save(player);
        return new IdResponse(player.getId());
    }
}
