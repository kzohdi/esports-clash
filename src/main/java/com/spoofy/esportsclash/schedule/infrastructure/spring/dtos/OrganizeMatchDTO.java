package com.spoofy.esportsclash.schedule.infrastructure.spring.dtos;

import java.time.LocalDate;

public record OrganizeMatchDTO(
        LocalDate date,
        String moment,
        String firstTeamId,
        String secondTeamId
) {
}
