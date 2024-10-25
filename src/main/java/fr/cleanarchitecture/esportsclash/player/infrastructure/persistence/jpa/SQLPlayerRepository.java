package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.jpa;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
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
        var playerQuery = dataAccessor.findById(id);

        if (playerQuery.isEmpty()) {
            throw new NotFoundException("Player not found");
        }

        var sqlPlayer = playerQuery.get();

        return Optional.of(new Player(
                sqlPlayer.getId(),
                sqlPlayer.getName()
            ));
    }

    @Override
    public void save(Player player) {
        var sqlPlayer = new SQLPlayer(
                player.getId(), player.getName());

        dataAccessor.save(sqlPlayer);
    }
}
