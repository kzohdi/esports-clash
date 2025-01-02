package com.spoofy.esportsclash.team.application.ports;

import com.spoofy.esportsclash.team.domain.viewmodels.TeamViewModel;

public interface TeamQueries {
    TeamViewModel getTeamById(String id);
}
