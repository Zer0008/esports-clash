package fr.cleanarchitecture.esportsclash.player.infrastructure.spring;

import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.application.usecases.CreatePlayerCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {

    @Bean
    public CreatePlayerCommandHandler createPlayerUseCase(PlayerRepository inMemoryPlayerRepository) {
        return new CreatePlayerCommandHandler(inMemoryPlayerRepository);
    }
}
