package fr.cleanarchitecture.esportsclash.player.application.ports;

import fr.cleanarchitecture.esportsclash.player.domain.model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findById(String id);

    void save(Player player);
}
