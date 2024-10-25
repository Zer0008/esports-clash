package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPlayerRepository implements PlayerRepository {

    private Map<String, Player> players = new HashMap<>();

    @Override
    public Optional<Player> findById(String id) {
        return Optional.ofNullable(players.get(id));
    }

    @Override
    public void save(Player player) {
        this.players.put(player.getId(), player);
    }
}
