package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory.InMemoryPlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerConfiguration {

    @Bean
    public PlayerRepository playerRepository() {
        return new InMemoryPlayerRepository();
    }
}
