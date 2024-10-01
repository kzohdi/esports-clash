package com.spoofy.esportclash.team.application.ports;

import com.spoofy.esportclash.team.domain.viewmodel.TeamViewModel;

public interface TeamQueries {
    TeamViewModel getTeamById(String id);
}
