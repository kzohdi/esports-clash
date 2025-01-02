package com.spoofy.esportsclash.schedule.application.ports;

import com.spoofy.esportsclash.core.infrastructure.persistence.BaseRepository;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;

import java.time.LocalDate;
import java.util.Optional;

public interface ScheduleDayRepository extends BaseRepository<ScheduleDay> {
    Optional<ScheduleDay> findByDate(LocalDate date);

    Optional<ScheduleDay> findByMatchId(String matchId);
}
