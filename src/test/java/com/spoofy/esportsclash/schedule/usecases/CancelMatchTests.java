package com.spoofy.esportsclash.schedule.usecases;

import com.spoofy.esportsclash.core.domain.exceptions.NotFoundException;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.application.usecases.CancelMatchCommand;
import com.spoofy.esportsclash.schedule.application.usecases.CancelMatchCommandHandler;
import com.spoofy.esportsclash.schedule.domain.models.Moment;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import com.spoofy.esportsclash.schedule.infrastructure.persistence.ram.InMemoryScheduleDayRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CancelMatchTests {

    private final ScheduleDayRepository scheduleDayRepository = new InMemoryScheduleDayRepository();
    private final CancelMatchCommandHandler commandHandler = new CancelMatchCommandHandler(scheduleDayRepository);

    @Test
    void shouldCancelMatch() {
        // Given
        var team1 = createTeam("t1");
        var team2 = createTeam("t2");
        var team3 = createTeam("t3");
        var team4 = createTeam("t4");

        var date = LocalDate.parse("2024-01-01");

        var scheduleDay = new ScheduleDay("1", date);
        scheduleDay.organize(team1, team2, Moment.MORNING);
        var match2 = scheduleDay.organize(team3, team4, Moment.AFTERNOON);
        scheduleDayRepository.save(scheduleDay);

        var command = new CancelMatchCommand(match2.getId());

        // When
        commandHandler.handle(command);

        // Then
        var updatedScheduleDay = scheduleDayRepository.findByDate(date).get();
        assertThat(updatedScheduleDay.getAt(Moment.MORNING)).isPresent();
        assertThat(updatedScheduleDay.getAt(Moment.AFTERNOON)).isEmpty();
    }

    @Test
    void whenCancelLastMatchOfDay_shouldDeleteScheduleDay() {
        // Given
        var team1 = createTeam("t1");
        var team2 = createTeam("t2");

        var date = LocalDate.parse("2024-01-01");

        var scheduleDay = new ScheduleDay("1", date);
        var match = scheduleDay.organize(team1, team2, Moment.MORNING);
        scheduleDayRepository.save(scheduleDay);

        var command = new CancelMatchCommand(match.getId());

        // When
        commandHandler.handle(command);

        // Then
        var scheduleDayQuery = scheduleDayRepository.findByDate(date);
        assertThat(scheduleDayQuery).isEmpty();
    }

    @Test
    void whenScheduleDayNotFound_shouldThrow() {
        // Given
        var command = new CancelMatchCommand("match1");

        // When
        // Then
        assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The entity Match with the id match1 was not found");
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
