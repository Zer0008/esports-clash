package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPlayerRepository implements PlayerRepository {

    private Map<String, Player> players = new HashMap<>();

    @Override
    public Player findById(String id) {
        return players.get(id);
    }

    @Override
    public void save(Player player) {
        this.players.put(player.getId(), player);
    }
}
