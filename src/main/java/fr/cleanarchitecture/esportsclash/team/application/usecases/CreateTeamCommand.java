package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;
import fr.cleanarchitecture.esportsclash.player.domain.viewmodel.IdResponse;

public class CreateTeamCommand implements Command<IdResponse> {
    private final String teamName;

    public CreateTeamCommand(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
