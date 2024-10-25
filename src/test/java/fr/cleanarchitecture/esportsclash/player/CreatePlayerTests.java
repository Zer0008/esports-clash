package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.player.application.usecases.CreatePlayerCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.Test;

public class CreatePlayerTests {

    @Test
    public void shouldCreatePlayer() {
        var playerRepository = new InMemoryPlayerRepository();
        var useCase = new CreatePlayerCommandHandler(playerRepository);
        var command = new CreatePlayerCommand("name");

        var response = useCase.handle(command);

        var expectedPlayer = new Player(response.getId(), "name");
        Player actualPlayer = playerRepository.findById(expectedPlayer.getId()).get();
        Assert.assertEquals(expectedPlayer.getName(), actualPlayer.getName());
    }
}
