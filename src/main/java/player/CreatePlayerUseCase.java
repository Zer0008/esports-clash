package player;

import java.util.UUID;

public class CreatePlayerUseCase {

    private final PlayerRepository playerRepository;

    public CreatePlayerUseCase(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public String execute(String name) {
        var player = new Player(UUID.randomUUID().toString(), name);
        playerRepository.save(player);
        return player.getId();
    }
}
