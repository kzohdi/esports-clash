package com.spoofy.esportsclash.team.infrastructure.persistence.jpa;

import com.spoofy.esportsclash.core.infrastructure.persistence.jpa.SQLBaseEntityMapper;
import com.spoofy.esportsclash.team.domain.model.Team;
import com.spoofy.esportsclash.team.domain.viewmodel.TeamViewModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SQLTeamMapper extends SQLBaseEntityMapper<Team, SQLTeam> {

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    SQLTeam toEntity(Team team);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    Team toModel(SQLTeam entity);

    @AfterMapping
    default void setTeamId(@MappingTarget SQLTeam sqlTeam) {
        var teamId = sqlTeam.getId();

        sqlTeam.getMembers().forEach(member -> member.setTeamId(teamId));
    }

    TeamViewModel toViewModel(SQLTeam sqlTeam);

    @Mapping(target = "playerName", source = "player.name")
    TeamViewModel.TeamMemberViewModel toTeamMemberViewModel(SQLTeam.SQLTeamMember sqlTeamMember);

}
