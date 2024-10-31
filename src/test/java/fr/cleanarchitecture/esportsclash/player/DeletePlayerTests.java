package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.usecases.DeletePlayerCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.DeletePlayerCommandHandler;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.Test;

public class DeletePlayerTests {

    @Test
    public void shouldDeletePlayer() {
        var playerRepository = new InMemoryPlayerRepository();
        var player = new Player("123", "old name");
        playerRepository.save(player);

        var deletePlayerCommand = new DeletePlayerCommand(player.getId());
        var deletePlayerCommandHandler = new DeletePlayerCommandHandler(playerRepository);
        deletePlayerCommandHandler.handle(deletePlayerCommand);

        var actualPlayer = playerRepository.findById(player.getId());
        Assert.assertTrue(actualPlayer.isEmpty());
    }

    @Test
    public void whenPlayerDoesNotExists_shouldThrowException() {
        var playerRepository = new InMemoryPlayerRepository();
        var deletePlayerCommand = new DeletePlayerCommand("garbage");
        var deletePlayerCommandeHandler = new DeletePlayerCommandHandler(playerRepository);

        Assert.assertThrows(
                "Player not found",
                NotFoundException.class,
                () -> deletePlayerCommandeHandler.handle(deletePlayerCommand)
        );
    }
}
