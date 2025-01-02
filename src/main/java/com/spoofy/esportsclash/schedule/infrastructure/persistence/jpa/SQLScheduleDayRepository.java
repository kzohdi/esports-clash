package com.spoofy.esportsclash.schedule.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseRepository;
import com.spoofy.esportsclash.schedule.application.ports.ScheduleDayRepository;
import com.spoofy.esportsclash.schedule.domain.models.ScheduleDay;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Optional;

public class SQLScheduleDayRepository extends SQLBaseRepository<ScheduleDay> implements ScheduleDayRepository {

    public SQLScheduleDayRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<ScheduleDay> getEntityClass() {
        return ScheduleDay.class;
    }

    @Override
    public Optional<ScheduleDay> findByDate(LocalDate date) {
        return entityManager.createQuery(
                        "SELECT s FROM ScheduleDay s " +
                                "WHERE s.day = :date"
                        , ScheduleDay.class)
                .setParameter("date", date)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<ScheduleDay> findByMatchId(String matchId) {
        return entityManager.createQuery(
                        "SELECT s FROM ScheduleDay s " +
                                "JOIN FETCH s.matches m " +
                                "WHERE m.id = :matchId"
                        , ScheduleDay.class)
                .setParameter("matchId", matchId)
                .getResultList()
                .stream()
                .findFirst();
    }
}
