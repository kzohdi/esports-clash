package com.spoofy.esportclash.team.infrastucture.spring.config;

import com.spoofy.esportclash.player.application.ports.PlayerRepository;
import com.spoofy.esportclash.team.application.ports.TeamQueries;
import com.spoofy.esportclash.team.application.ports.TeamRepository;
import com.spoofy.esportclash.team.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamUseCasesConfiguration {

    @Bean
    public CreateTeamCommandHandler createTeamCommandHandler(TeamRepository teamRepository) {
        return new CreateTeamCommandHandler(teamRepository);
    }

    @Bean
    public DeleteTeamCommandHandler deleteTeamCommandHandler(TeamRepository teamRepository) {
        return new DeleteTeamCommandHandler(teamRepository);
    }

    @Bean
    public AddPlayerToTeamCommandHandler addPlayerToTeamCommandHandler(
            TeamRepository teamRepository,
            PlayerRepository playerRepository
    ) {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    public RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(TeamRepository teamRepository) {
        return new RemovePlayerFromTeamCommandHandler(teamRepository);
    }

    @Bean
    public GetTeamByIdCommandHandler getTeamByIdCommandHandler(TeamQueries teamQueries) {
        return new GetTeamByIdCommandHandler(teamQueries);
    }
}
