package com.spoofy.esportsclash.team.infrastructure.spring.config;

import com.spoofy.esportsclash.player.application.ports.PlayerRepository;
import com.spoofy.esportsclash.team.application.ports.TeamQueries;
import com.spoofy.esportsclash.team.application.ports.TeamRepository;
import com.spoofy.esportsclash.team.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamUseCasesConfiguration {

    @Bean
    CreateTeamCommandHandler createTeamCommandHandler(TeamRepository repository) {
        return new CreateTeamCommandHandler(repository);
    }

    @Bean
    DeleteTeamCommandHandler deleteTeamCommandHandler(TeamRepository repository) {
        return new DeleteTeamCommandHandler(repository);
    }

    @Bean
    AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(TeamRepository teamRepository, PlayerRepository playerRepository) {
        return new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    GetTeamByIdCommandHandler getTeamByIdCommandHandler(TeamQueries teamQueries) {
        return new GetTeamByIdCommandHandler(teamQueries);
    }
}
