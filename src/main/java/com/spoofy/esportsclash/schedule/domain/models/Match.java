package com.spoofy.esportsclash.schedule.domain.models;

import com.spoofy.esportsclash.core.domain.models.BaseModel;
import com.spoofy.esportsclash.team.domain.models.Team;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match extends BaseModel<Match> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_team_id", insertable = false, updatable = false)
    @MapsId("firstTeamId")
    private Team firstTeam;

    @Column(name = "first_team_id")
    private String firstTeamId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_team_id", insertable = false, updatable = false)
    @MapsId("secondTeamId")
    private Team secondTeam;

    @Column(name = "second_team_id")
    private String secondTeamId;

    @Column(name = "schedule_day_id")
    private String scheduleDayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_day_id", insertable = false, updatable = false)
    @MapsId("scheduleDayId")
    private ScheduleDay scheduleDay;

    public Match() {

    }

    public Match(String id, String scheduleDayId, String firstTeamId, String secondTeamId) {
        super(id);
        this.scheduleDayId = scheduleDayId;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
    }

    public String getFirstTeamId() {
        return firstTeamId;
    }

    public String getSecondTeamId() {
        return secondTeamId;
    }

    public boolean featuresTeam(String teamId) {
        return firstTeamId.equals(teamId) || secondTeamId.equals(teamId);
    }

    @Override
    public Match deepClone() {
        return new Match(id, scheduleDayId, firstTeamId, secondTeamId);
    }
}
