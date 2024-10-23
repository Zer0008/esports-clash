package fr.cleanarchitecture.esportsclash.player;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerUseCaseConfiguration {

    @Bean
    public CreatePlayerUseCase createPlayerUseCase(PlayerRepository playerRepository) {
        return new CreatePlayerUseCase(playerRepository);
    }
}
