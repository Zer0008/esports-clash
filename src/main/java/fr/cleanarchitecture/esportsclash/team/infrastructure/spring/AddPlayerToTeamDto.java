package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.team.domain.model.Role;

public class AddPlayerToTeamDto {
    private String teamId;
    private String playerId;
    private Role role;

    public AddPlayerToTeamDto() {}

    public AddPlayerToTeamDto(String teamId, String playerId, Role role) {
        this.teamId = teamId;
        this.playerId = playerId;
        this.role = role;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Role getRole() {
        return role;
    }
}
