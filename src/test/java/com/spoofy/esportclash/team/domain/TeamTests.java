package com.spoofy.esportclash.team.domain;

import com.spoofy.esportclash.team.domain.model.Role;
import com.spoofy.esportclash.team.domain.model.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTests {

    @Nested
    class AddMember {
        @Test
        void shouldJoinTeam() {
            var team = new Team("team1", "Team rocket");

            team.addMember("player1", Role.TOP);

            assertTrue(team.hasMember("player1", Role.TOP));
        }

        @Test
        void whenPlayerAlreadyInTeam_shouldFail() {
            var team = new Team("team1", "Team rocket");

            team.addMember("player1", Role.TOP);

            var exception = assertThrows(IllegalArgumentException.class, () ->
                    team.addMember("player1", Role.BOTTOM));

            assertEquals("Player player1 is already in team", exception.getMessage());
        }

        @Test
        void whenRoleAlreadyTaken_shouldFail() {
            var team = new Team("team1", "Team rocket");

            team.addMember("player1", Role.TOP);

            var exception = assertThrows(IllegalArgumentException.class, () ->
                    team.addMember("player2", Role.TOP));

            assertEquals("Role TOP is already taken", exception.getMessage());
        }
    }

    @Nested
    class RemoveMember {
        @Test
        void shouldRemoveMember() {
            var team = new Team("team1", "Team rocket");
            team.addMember("player1", Role.TOP);

            team.removeMember("player1");

            assertFalse(team.hasMember("player1", Role.TOP));
        }

        @Test
        void whenPlayerNotInTeam_shouldFail() {
            var team = new Team("team1", "Team rocket");

            var exception = assertThrows(IllegalArgumentException.class, () ->
                    team.removeMember("player1"));

            assertEquals("Player player1 not in team", exception.getMessage());
        }
    }
}
