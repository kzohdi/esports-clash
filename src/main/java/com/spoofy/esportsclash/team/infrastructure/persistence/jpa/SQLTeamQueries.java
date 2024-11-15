package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SQLTeamQueries implements TeamQueries {

    private final SQLTeamDataAccessor teamDataAccessor;

    private final SQLTeamMapper mapper;

    @Override
    public Optional<TeamViewModel> getTeamById(String id) {
        return teamDataAccessor.findByIdAndFetchMembersAndPlayers(id)
                .map(mapper::toViewModel);


    }
}
