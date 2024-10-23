package fr.cleanarchitecture.esportsclash.player;

import org.junit.Assert;
import org.junit.Test;
import player.CreatePlayerUseCase;
import player.Player;
import player.PlayerRepository;

public class CreatePlayerTests {
    @Test
    public void testCreatePlayer() {
        var playerRepository = new PlayerRepository();
        var useCase = new CreatePlayerUseCase(playerRepository);
        var id = useCase.execute("name");
        var expectedPlayer = new Player(id, "name");
        Player actualPlayer = playerRepository.find(expectedPlayer.getId());
        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
