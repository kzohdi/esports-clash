package com.spoofy.esportsclash.team.domain;

import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TeamTests {

    @Test
    void whenTeamDoesNotHaveEnoughMembers_shouldBeIncomplete() {
        // Given
        var team = new Team("team1", "Spoofy Team");
        team.addMember("1", Role.TOP);

        // When
        var result = team.isIncomplete();

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenTeamHasEnoughMembers_shouldBeComplete() {
        // Given
        var team = new Team("team1", "Spoofy Team");
        team.addMember("1", Role.TOP);
        team.addMember("2", Role.BOTTOM);
        team.addMember("3", Role.MIDDLE);
        team.addMember("4", Role.JUNGLE);
        team.addMember("5", Role.SUPPORT);

        // When
        var result = team.isIncomplete();

        // Then
        assertThat(result).isFalse();
    }

    @Nested
    class AddMember {
        @Test
        void shouldAddPlayerToTeam() {
            // Given
            var team = new Team("team1", "Spoofy Team");

            // When
            team.addMember("player1", Role.TOP);

            // Then
            assertThat(team.hasMember("player1", Role.TOP)).isTrue();
        }

        @Test
        void whenPlayerAlreadyInTeam_shouldThrow() {
            // Given
            var team = new Team("team1", "Spoofy Team");
            team.addMember("player1", Role.BOTTOM);

            // When
            // Then
            assertThatThrownBy(() -> team.addMember("player1", Role.TOP))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("The player is already in the team");
        }

        @Test
        void whenRoleAlreadyTaken_shouldThrow() {
            // Given
            var team = new Team("team1", "Spoofy Team");
            team.addMember("player1", Role.TOP);

            // When
            // Then
            assertThatThrownBy(() -> team.addMember("player2", Role.TOP))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("The role is already in the team");
        }
    }

    @Nested
    class RemoveMember {
        @Test
        void shouldRemovePlayerFromTeam() {
            // Given
            var team = new Team("team1", "Spoofy Team");
            team.addMember("player1", Role.TOP);

            // When
            team.removeMember("player1");

            // Then
            assertThat(team.hasMember("player1", Role.TOP)).isFalse();
        }

        @Test
        void whenPlayerNotInTeam_shouldThrow() {
            // Given
            var team = new Team("team1", "Spoofy Team");

            // When
            // Then
            assertThatThrownBy(() -> team.removeMember("player1"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("The player is not in the team");
        }
    }
}
