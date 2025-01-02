package com.spoofy.esportsclash.schedule.e2e;

import com.spoofy.esportsclash.IntegrationTestTemplate;
import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.player.domain.models.Player;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.domain.models.Moment;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import com.spoofy.esportsclash.schedule.infrastructure.spring.dtos.CancelMatchDTO;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.domain.models.Role;
import com.spoofy.esportsclash.team.domain.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CancelMatchE2ETests extends IntegrationTestTemplate {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScheduleDayRepository scheduleDayRepository;

    @Test
    void shouldCancelMatch() throws Exception {
        // Given
        var team1 = createTeam("t1");
        teamRepository.save(team1);
        var team2 = createTeam("t2");
        teamRepository.save(team2);
        var scheduleDay = new ScheduleDay(UUID.randomUUID().toString(), LocalDate.parse("2024-01-01"));
        var match = scheduleDay.organize(team1, team2, Moment.MORNING);
        scheduleDayRepository.save(scheduleDay);

        var dto = new CancelMatchDTO(match.getId());

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/schedules/cancel-match")
                        .header("Authorization", createJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        var scheduleDayQuery = scheduleDayRepository.findById(scheduleDay.getId());
        assertThat(scheduleDayQuery).isEmpty();
    }

    private Team createTeam(String id) {
        var name = "Team " + id;
        var team = new Team(id, name);

        for (var role : Role.values()) {
            var player = new Player(UUID.randomUUID().toString(), "Player");
            playerRepository.save(player);

            team.addMember(player.getId(), role);

        }

        return team;
    }
}
