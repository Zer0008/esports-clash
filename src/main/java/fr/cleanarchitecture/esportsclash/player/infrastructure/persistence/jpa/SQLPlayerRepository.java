package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.jpa;

import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;

import java.util.Optional;

public class SQLPlayerRepository implements PlayerRepository {
    private final SQLPlayerDataAccessor dataAccessor;

    public SQLPlayerRepository(SQLPlayerDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    @Override
    public Optional<Player> findById(String id) {
        var SQLPlayer = dataAccessor.findById(id);

        return SQLPlayer.map(sqlPlayer -> new Player(sqlPlayer.getId(), sqlPlayer.getName()));
    }

    @Override
    public void save(Player player) {
        var sqlPlayer = new SQLPlayer(
                player.getId(), player.getName());

        dataAccessor.save(sqlPlayer);
    }

    @Override
    public void delete(Player player) {
        var SQLPlayer = new SQLPlayer(
                player.getId(), player.getName()
        );
        dataAccessor.delete(SQLPlayer);
    }

    @Override
    public void clear() {
        dataAccessor.deleteAll();
    }
}
