package fr.cleanarchitecture.esportsclash.player;

import org.junit.Assert;
import org.junit.Test;

public class CreatePlayerTests {

    @Test
    public void shouldCreatePlayer() {
        var playerRepository = new PlayerRepository();
        var useCase = new CreatePlayerUseCase(playerRepository);
        var response = useCase.execute("name");
        var expectedPlayer = new Player(response.getId(), "name");
        Player actualPlayer = playerRepository.find(expectedPlayer.getId());
        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
