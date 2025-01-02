package com.spoofy.esportsclash.schedule.infrastructure.persistence.ram;

import com.spoofy.esportsclash.core.infrastructure.persistence.ram.InMemoryBaseRepository;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class InMemoryScheduleDayRepository extends InMemoryBaseRepository<ScheduleDay> implements ScheduleDayRepository {

    @Override
    public Optional<ScheduleDay> findByDate(LocalDate date) {
        return models.values().stream()
                .filter(scheduleDay -> scheduleDay.getDay().isEqual(date))
                .findFirst()
                .map(ScheduleDay::deepClone);
    }

    @Override
    public Optional<ScheduleDay> findByMatchId(String matchId) {
        return models.values().stream()
                .filter(scheduleDay -> scheduleDay.containsMatch(matchId))
                .findFirst();
    }
}
