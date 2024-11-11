package fr.cleanarchitecture.esportsclash.team.application.usecases;

import an.awesome.pipelinr.Command;

public class DeleteTeamCommand implements Command<Void> {
    private String teamId;

    public DeleteTeamCommand(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }
}
