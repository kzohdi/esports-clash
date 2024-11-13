package com.spoofy.esportsclash.team.application.ports;

import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;

import java.util.Optional;

public interface TeamQueries {
    Optional<TeamViewModel> getTeamById(String id);
}
