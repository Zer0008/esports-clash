package fr.cleanarchitecture.esportsclash.player;

import fr.cleanarchitecture.esportsclash.core.domain.exceptions.NotFoundException;
import fr.cleanarchitecture.esportsclash.player.application.usecases.RenamePlayerCommand;
import fr.cleanarchitecture.esportsclash.player.application.usecases.RenamePlayerCommandHandler;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import org.junit.Assert;
import org.junit.Test;

public class RenamePlayerTests {

    @Test
    public void shouldRenamePlayer() {
        var playerRepository = new InMemoryPlayerRepository();
        var player = new Player("123", "old name");
        playerRepository.save(player);

        var renamePlayerCommand = new RenamePlayerCommand(player.getId(), "new name");
        var renamePlayerCommandHandler = new RenamePlayerCommandHandler(playerRepository);
        renamePlayerCommandHandler.handle(renamePlayerCommand);

        Player actualPlayer = playerRepository.findById(player.getId()).get();
        Assert.assertEquals(renamePlayerCommand.getName(), actualPlayer.getName());
    }

    @Test
    public void whenPlayerDoesNotExists_shouldThrowException() {
        var playerRepository = new InMemoryPlayerRepository();
        var renamePlayerCommand = new RenamePlayerCommand("garbage", "new name");
        var renamePlayerCommandHandler = new RenamePlayerCommandHandler(playerRepository);

        Assert.assertThrows(
                "Player not found",
                NotFoundException.class,
                () -> renamePlayerCommandHandler.handle(renamePlayerCommand)
        );
    }
}
