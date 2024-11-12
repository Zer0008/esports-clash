package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

public class RemovePlayerFromTeamDto {
    private String teamId;
    private String playerId;

    public RemovePlayerFromTeamDto() {}

    public RemovePlayerFromTeamDto(String teamId, String playerId) {
        this.teamId = teamId;
        this.playerId = playerId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
