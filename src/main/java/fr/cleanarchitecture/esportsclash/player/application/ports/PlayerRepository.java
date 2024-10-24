package fr.cleanarchitecture.esportsclash.player.application.ports;

import fr.cleanarchitecture.esportsclash.domain.model.Player;

public interface PlayerRepository {

    Player findById(String id);

    void save(fr.cleanarchitecture.esportsclash.domain.model.Player player);
}
