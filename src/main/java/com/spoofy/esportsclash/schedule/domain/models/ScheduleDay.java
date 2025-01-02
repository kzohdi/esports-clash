package com.spoofy.esportsclash.schedule.domain.models;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import com.spoofy.esportsclash.team.domain.models.Team;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "schedule_days")
public class ScheduleDay extends BaseModel<ScheduleDay> {

    @Column
    private LocalDate day;

    @OneToMany(
            mappedBy = "scheduleDay",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Moment, Match> matches;

    public ScheduleDay() {
    }

    public ScheduleDay(String id, LocalDate day) {
        super(id);
        this.day = day;
        matches = new EnumMap<>(Moment.class);
    }

    private ScheduleDay(String id, LocalDate day, Map<Moment, Match> matches) {
        super(id);
        this.day = day;
        this.matches = matches;
    }

    public LocalDate getDay() {
        return day;
    }

    public Map<Moment, Match> getMatches() {
        return matches;
    }

    public Match organize(Team team1, Team team2, Moment moment) {
        if (matches.containsKey(moment)) {
            throw new IllegalStateException("There is already a match for the moment: " + moment);
        }

        if (team1.isIncomplete() || team2.isIncomplete()) {
            throw new IllegalStateException("At least one of the teams is incomplete");
        }

        var anyTeamAlreadyPlayed = matches.values()
                .stream()
                .anyMatch(match -> match.featuresTeam(team1.getId()) || match.featuresTeam(team2.getId()));

        if (anyTeamAlreadyPlayed) {
            throw new IllegalStateException("At least one of the teams has already played on this day");
        }

        var match = new Match(
                UUID.randomUUID().toString(),
                id,
                team1.getId(),
                team2.getId()
        );

        matches.put(moment, match);

        return match;
    }

    public void cancel(String matchId) {
        var moment = matches.keySet().stream()
                .filter(m -> matches.get(m).getId().equals(matchId))
                .findFirst();

        moment.ifPresent(matches::remove);
    }

    public Optional<Match> getAt(Moment moment) {
        return Optional.ofNullable(matches.get(moment));
    }

    public boolean containsMatch(String matchId) {
        return matches.values()
                .stream()
                .anyMatch(match -> match.getId().equals(matchId));
    }

    public boolean hasMatches() {
        return !matches.isEmpty();
    }

    @Override
    public ScheduleDay deepClone() {
        return new ScheduleDay(
                id,
                day,
                cloneMatches()
        );
    }

    private Map<Moment, Match> cloneMatches() {
        return matches.keySet()
                .stream()
                .collect(
                        () -> new EnumMap<>(Moment.class),
                        (map, moment) ->
                                map.put(moment, matches.get(moment).deepClone()),
                        Map::putAll
                );
    }
}
