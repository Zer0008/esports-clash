package fr.cleanarchitecture.esportsclash.team.domain.model;

import fr.cleanarchitecture.esportsclash.core.domain.model.BaseEntity;

import java.util.Set;
import java.util.UUID;

public class Team extends BaseEntity {

    private final String teamId;
    private final String teamName;
    private final Set<TeamMember> members;

    public Team(String teamId, String teamName, Set<TeamMember> members) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.members = members;
    }

    public void addMember(String playerId, Role role) {
        var playerAlreadyInTheTeam = this.members.stream().anyMatch(teamMember -> teamMember.playerId.equals(playerId));
        var roleAlreadyInUseInTheTeam = this.members.stream().anyMatch(teamMember -> teamMember.role.equals(role));

        if (playerAlreadyInTheTeam) {
            throw new IllegalArgumentException("Player " + playerId + " already in the team");
        }

        if (roleAlreadyInUseInTheTeam) {
            throw new IllegalArgumentException("Role " + role + " already taken in the team");
        }

        var member = new TeamMember(
                UUID.randomUUID().toString(),
                playerId,
                role);
        this.members.add(member);
    }

    public void removeMember(String playerId) {
        if (this.members.stream().noneMatch(teamMember -> teamMember.playerId.equals(playerId))) {
            throw new IllegalArgumentException("Player " + playerId + " is not in the team");
        }
        this.members.removeIf(teamMember -> teamMember.playerId.equals(playerId));
    }

    public boolean hasMember(String playerId, Role role) {
        return this.members
                .stream()
                .anyMatch(
                    teamMember -> teamMember.playerId.equals(playerId) &&
                            teamMember.role.equals(role)
        );
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public Set<TeamMember> getMembers() {
        return members;
    }

    static class TeamMember {
        private final String id;
        private final String playerId;
        private final Role role;

        public TeamMember(String id, String playerId, Role role) {
            this.id = id;
            this.playerId = playerId;
            this.role = role;
        }
    }
}
