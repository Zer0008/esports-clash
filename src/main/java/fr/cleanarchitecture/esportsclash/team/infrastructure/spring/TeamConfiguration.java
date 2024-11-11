package fr.cleanarchitecture.esportsclash.team.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory.InMemoryTeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeamConfiguration {

    @Bean
    public TeamRepository teamReposiroty() {
        return new InMemoryTeamRepository();
    }
}
