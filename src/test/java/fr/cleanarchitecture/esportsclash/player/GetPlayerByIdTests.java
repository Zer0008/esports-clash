package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.usecases.GetPlayerByIdCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.GetPlayerByIdCommandHandler;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.Test;

public class GetPlayerByIdTests {

    @Test
    public void shouldGetPlayerById() {
        var playerRepository = new InMemoryPlayerRepository();
        var player = new Player("123", "player");
        playerRepository.savePlayer(player);
        var useCase = new GetPlayerByIdCommandHandler(playerRepository);
        var command = new GetPlayerByIdCommand("123");

        var playerViewModel = useCase.handle(command);

        Assert.assertEquals(playerViewModel.getName(), player.getName());
    }

    @Test
    public void whenPlayerNotFound_shouldThrowException() {
        var playerRepository = new InMemoryPlayerRepository();
        var useCase = new GetPlayerByIdCommandHandler(playerRepository);
        var command = new GetPlayerByIdCommand("123");

        Assert.assertThrows(
                "Player not found",
                NotFoundException.class,
                () -> useCase.handle(command)
        );
    }
}
