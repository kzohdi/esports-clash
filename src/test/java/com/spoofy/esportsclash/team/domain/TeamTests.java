package com.spoofy.esportsclash.team.domain;

import com.spoofy.esportsclash.core.domain.exceptions.BadRequestException;
import com.spoofy.esportsclash.team.domain.model.Role;
import com.spoofy.esportsclash.team.domain.model.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTests {

    @Nested
    class AddMember {
        @Test
        void shouldAddPlayerToTeam() {
            // Given
            var team = new Team("team1", "Team rocket");
            var playerId = "player1";

            // When
            team.addMember(playerId, Role.TOP);

            // Then
            assertTrue(team.hasMember(playerId));
        }

        @Test
        void whenPlayerAlreadyInTeam_shouldThrow() {
            // Given
            var team = new Team("team1", "Team rocket");
            var playerId = "player1";
            team.addMember(playerId, Role.TOP);

            // When
            var exception = assertThrows(BadRequestException.class, () -> team.addMember(playerId, Role.BOTTOM));

            // Then
            assertEquals("Player is already in the team", exception.getMessage());
        }

        @Test
        void whenRoleIsTaken_shouldThrow() {
            // Given
            var team = new Team("team1", "Team rocket");
            var playerId = "player1";
            team.addMember(playerId, Role.TOP);

            // When
            var exception = assertThrows(BadRequestException.class, () -> team.addMember("player2", Role.TOP));

            // Then
            assertEquals("Role is already taken", exception.getMessage());
        }
    }

    @Nested
    class RemoveMember {

        @Test
        void shouldRemoveMember() {
            // Given
            var team = new Team("team1", "Team rocket");
            var playerId = "player1";
            team.addMember(playerId, Role.TOP);

            // When
            team.removeMember(playerId);

            // Then
            assertFalse(team.hasMember(playerId));
        }

        @Test
        void whenPlayerNotInTeam_shouldFail() {
            // Given
            var team = new Team("team1", "Team rocket");
            var playerId = "player1";

            // When
            var exception = assertThrows(BadRequestException.class, () -> team.removeMember(playerId));

            // Then
            assertEquals("Player is not in the team", exception.getMessage());
        }
    }
}
