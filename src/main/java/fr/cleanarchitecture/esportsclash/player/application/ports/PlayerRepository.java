package fr.cleanarchitecture.esportsclash.player.application.ports;

import fr.cleanarchitecture.esportsclash.player.domain.model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findById(String id);

    void savePlayer(Player player);

    void deletePlayer(Player player);
}
