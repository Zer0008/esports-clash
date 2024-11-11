package fr.cleanarchitecture.esportsclash.team.domain;

import fr.cleanarchitecture.esportsclash.team.domain.model.Role;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class TeamTests {

    @Nested
    public class AddMemberInTheTeam {

        @Test
        public void shouldJoinTeam() {
            var team = new Team("123", "Team1", new HashSet<>());
            team.addMember("Member1", Role.TOP);

            Assert.assertTrue(team.hasMember("Member1", Role.TOP));
        }

        @Test
        public void whenThePlayerIsAlreadyInTheTeam_shouldFail() {
            var team = new Team("123", "Team1", new HashSet<>());
            team.addMember("playerId", Role.TOP);
            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.addMember("playerId", Role.BOTTOM)
            );

            Assertions.assertEquals(exception.getMessage(), "Player playerId already in the team");
        }

        @Test
        public void whenTheRoleIsAlreadyTakenInTheTeam_shouldFail() {
            var team = new Team("123", "Team1", new HashSet<>());
            team.addMember("playerId1", Role.TOP);
            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.addMember("playerId2", Role.TOP)
            );

            Assertions.assertEquals(exception.getMessage(), "Role TOP already taken in the team");
        }
    }

    @Nested
    public class RemoveMemberFromTeam {
        @Test
        public void shouldRemoveMemberFromTeam() {
            var team = new Team("123", "Team1", new HashSet<>());
            team.addMember("playerId", Role.TOP);

            team.removeMember("playerId");

            Assertions.assertFalse(team.hasMember("playerId", Role.TOP));
        }

        @Test
        public void whenPlayerIsNotInTheTeam_shouldFail() {
            var team = new Team("123", "Team1", new HashSet<>());
            team.addMember("playerId", Role.TOP);
            var exception = Assert.assertThrows(
                    IllegalArgumentException.class,
                    () -> team.removeMember("playerId2")
            );
            Assertions.assertEquals(exception.getMessage(), "Player playerId2 is not in the team");
        }
    }
}
