package fr.cleanarchitecture.esportsclash.player.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.PlayerViewModel;

public class GetPlayerByIdCommand implements Command<PlayerViewModel> {
    private final String playerId;

    public GetPlayerByIdCommand(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
