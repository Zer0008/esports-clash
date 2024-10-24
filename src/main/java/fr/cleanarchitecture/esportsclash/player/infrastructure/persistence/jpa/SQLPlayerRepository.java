package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.jpa;

import fr.cleanarchitecture.esportsclash.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;

public class SQLPlayerRepository implements PlayerRepository {
    private final SQLPlayerDataAccessor dataAccessor;

    public SQLPlayerRepository(SQLPlayerDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    @Override
    public Player findById(String id) {
        var playerQuery = dataAccessor.findById(id);

        if (playerQuery.isEmpty()) {
            return null;
        }

        var sqlPlayer = playerQuery.get();

        return new Player(
                sqlPlayer.getId(),
                sqlPlayer.getName()
            );
    }

    @Override
    public void save(Player player) {
        var sqlPlayer = new SQLPlayer(
                player.getId(), player.getName());

        dataAccessor.save(sqlPlayer);
    }
}
