package fr.cleanarchitecture.esportsclash.player.application.usecases;

import fr.cleanarchitecture.esportsclash.domain.model.Player;
import fr.cleanarchitecture.esportsclash.domain.viewmodel.IdResponse;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;

import java.util.UUID;

public class CreatePlayerUseCase {

    private final PlayerRepository inMemoryPlayerRepository;

    public CreatePlayerUseCase(PlayerRepository inMemoryPlayerRepository) {
        this.inMemoryPlayerRepository = inMemoryPlayerRepository;
    }

    public IdResponse execute(String name) {
        var player = new Player(UUID.randomUUID().toString(), name);
        inMemoryPlayerRepository.save(player);
        return new IdResponse(player.getId());
    }
}
