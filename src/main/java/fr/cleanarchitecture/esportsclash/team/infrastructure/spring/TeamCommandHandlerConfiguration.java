package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.application.usecases.AddPlayerToTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.application.usecases.CreateTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.application.usecases.DeleteTeamCommandHandler;
import fr.cleanarchitecture.esportsclash.team.application.usecases.RemovePlayerFromTeamCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamCommandHandlerConfiguration {
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
            TeamRepository teamRepository, PlayerRepository playerRepository) {
        return new AddPlayerToTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    public RemovePlayerFromTeamCommandHandler removePlayerFromTeamCommandHandler(
            TeamRepository teamRepository, PlayerRepository playerRepository) {
        return new RemovePlayerFromTeamCommandHandler(teamRepository, playerRepository);
    }
}
