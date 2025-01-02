package com.spoofy.esportsclash.schedule.domain;

import com.spoofy.esportsclash.schedule.domain.models.Moment;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ScheduleDayTests {

    private final ScheduleDay scheduleDay = new ScheduleDay("1", LocalDate.now());

    @Nested
    class OrganizeTests {
        @Test
        void shouldOrganize() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");

            // When
            scheduleDay.organize(team1, team2, Moment.MORNING);

            // Then
            var match = scheduleDay.getAt(Moment.MORNING);
            assertThat(match).isNotEmpty();
        }

        @Test
        void whenMomentIsUnavailable_shouldThrow() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");
            scheduleDay.organize(team1, team2, Moment.MORNING);


            var team3 = createCompleteTeam("t3");
            var team4 = createCompleteTeam("t4");

            // When
            // Then
            assertThatThrownBy(() -> scheduleDay.organize(team3, team4, Moment.MORNING))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("There is already a match for the moment: " + Moment.MORNING);
        }

        @Test
        void whenFirstTeamHasAlreadyPlayed_shouldThrow() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");
            scheduleDay.organize(team1, team2, Moment.MORNING);


            var team3 = createCompleteTeam("t3");

            // When
            // Then
            assertThatThrownBy(() -> scheduleDay.organize(team2, team3, Moment.AFTERNOON))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("At least one of the teams has already played on this day");
        }

        @Test
        void whenSecondTeamHasAlreadyPlayed_shouldThrow() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");
            scheduleDay.organize(team1, team2, Moment.MORNING);


            var team3 = createCompleteTeam("t3");

            // When
            // Then
            assertThatThrownBy(() -> scheduleDay.organize(team3, team2, Moment.AFTERNOON))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("At least one of the teams has already played on this day");
        }

        @Test
        void whenFirstTeamIsIncomplete_shouldThrow() {
            // Given
            var team1 = createIncompleteTeam("t1");
            var team2 = createCompleteTeam("t2");

            // When
            // Then
            assertThatThrownBy(() -> scheduleDay.organize(team1, team2, Moment.MORNING))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("At least one of the teams is incomplete");
        }

        @Test
        void whenSecondTeamIsIncomplete_shouldThrow() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createIncompleteTeam("t2");

            // When
            // Then
            assertThatThrownBy(() -> scheduleDay.organize(team1, team2, Moment.MORNING))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("At least one of the teams is incomplete");
        }
    }

    @Nested
    class CancelTests {

        @Test
        void shouldCancel() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");
            var organizedMatch = scheduleDay.organize(team1, team2, Moment.MORNING);

            // When
            scheduleDay.cancel(organizedMatch.getId());

            // Then
            var match = scheduleDay.getAt(Moment.MORNING);
            assertThat(match).isEmpty();
        }
    }

    @Nested
    class ContainsMatchTests {
        @Test
        void whenMatchIsPresent_shouldReturnTrue() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");

            var match = scheduleDay.organize(team1, team2, Moment.MORNING);

            // When
            var result = scheduleDay.containsMatch(match.getId());

            // Then
            assertThat(result).isTrue();
        }

        @Test
        void whenMatchIsNotPresent_shouldReturnFalse() {
            // When
            var result = scheduleDay.containsMatch("match1");

            // Then
            assertThat(result).isFalse();
        }
    }

    @Nested
    class HasMatchesTests {
        @Test
        void whenAtLeastOneMatchIsPresent_shouldReturnTrue() {
            // Given
            var team1 = createCompleteTeam("t1");
            var team2 = createCompleteTeam("t2");

            var match = scheduleDay.organize(team1, team2, Moment.MORNING);

            // When
            var result = scheduleDay.hasMatches();

            // Then
            assertThat(result).isTrue();
        }

        @Test
        void whenNoMatchIsPresent_shouldReturnFalse() {
            // When
            var result = scheduleDay.hasMatches();

            // Then
            assertThat(result).isFalse();
        }
    }

    private Team createCompleteTeam(String id) {
        var name = "Team " + id;
        var team = new Team(id, name);

        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.BOTTOM);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.JUNGLE);
        team.addMember(id + "-5", Role.SUPPORT);

        return team;
    }

    private Team createIncompleteTeam(String id) {
        var name = "Team " + id;
        var team = new Team(id, name);

        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.BOTTOM);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.JUNGLE);

        return team;
    }
}
