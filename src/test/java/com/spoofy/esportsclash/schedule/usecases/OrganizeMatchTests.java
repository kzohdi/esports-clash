package com.spoofy.esportsclash.schedule.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.application.usecases.OrganizeMatchCommand;
import com.spoofy.esportsclash.schedule.application.usecases.OrganizeMatchCommandHandler;
import com.spoofy.esportsclash.schedule.domain.models.Moment;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import com.spoofy.esportsclash.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import com.spoofy.esportsclash.team.infrastructure.persistence.ram.InMemoryTeamRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrganizeMatchTests {

    private final ScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();

    private final TeamRepository teamRepository = new InMemoryTeamRepository();

    private final OrganizeMatchCommandHandler commandHandler = new OrganizeMatchCommandHandler(scheduleDayRepository, teamRepository);

    @Test
    void shouldOrganizeMatch() {
        // Given
        var team1 = createTeam("t1");
        teamRepository.save(team1);
        var team2 = createTeam("t2");
        teamRepository.save(team2);
        var date = LocalDate.parse("2024-01-01");

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                team1.getId(),
                team2.getId()
        );

        // When
        var result = commandHandler.handle(command);

        // Then
        var scheduleDayQuery = scheduleDayRepository.findByDate(date);
        assertThat(scheduleDayQuery).isPresent();

        var scheduleDay = scheduleDayQuery.get();

        var matchQuery = scheduleDay.getAt(Moment.MORNING);
        assertThat(matchQuery).isPresent();

        var match = matchQuery.get();
        assertThat(match.getId()).isEqualTo(result.id());
        assertThat(match.getFirstTeamId()).isEqualTo(team1.getId());
        assertThat(match.getSecondTeamId()).isEqualTo(team2.getId());
    }

    @Test
    void whenScheduleDayAlreadyExists_shouldReuseIt() {
        // Given
        var team1 = createTeam("t1");
        teamRepository.save(team1);
        var team2 = createTeam("t2");
        teamRepository.save(team2);
        var date = LocalDate.parse("2024-01-01");

        var scheduleDay = new ScheduleDay("schedule1", date);
        scheduleDayRepository.save(scheduleDay);

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                team1.getId(),
                team2.getId()
        );

        // When
        var result = commandHandler.handle(command);

        // Then
        var updatedScheduleDay = scheduleDayRepository.findByDate(date).get();
        var matchQuery = updatedScheduleDay.getAt(Moment.MORNING);
        assertThat(matchQuery).isPresent();

        var match = matchQuery.get();
        assertThat(match.getId()).isEqualTo(result.id());
        assertThat(match.getFirstTeamId()).isEqualTo(team1.getId());
        assertThat(match.getSecondTeamId()).isEqualTo(team2.getId());
    }

    @Test
    void whenFirstTeamDoesNotExist_shouldThrow() {
        // Given
        var team1 = createTeam("t1");
        teamRepository.save(team1);
        var date = LocalDate.parse("2024-01-01");

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                team1.getId(),
                "t2"
        );

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Team with the id t2 was not found");
    }

    @Test
    void whenSecondTeamDoesNotExist_shouldThrow() {
        // Given
        var team1 = createTeam("t1");
        teamRepository.save(team1);
        var date = LocalDate.parse("2024-01-01");

        var command = new OrganizeMatchCommand(
                date,
                Moment.MORNING,
                "t2",
                team1.getId()
                );

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Team with the id t2 was not found");
    }

    private Team createTeam(String id) {
        var name = "Team " + id;
        var team = new Team(id, name);

        team.addMember(id + "-1", Role.TOP);
        team.addMember(id + "-2", Role.BOTTOM);
        team.addMember(id + "-3", Role.MIDDLE);
        team.addMember(id + "-4", Role.JUNGLE);
        team.addMember(id + "-5", Role.SUPPORT);

        return team;
    }
}
