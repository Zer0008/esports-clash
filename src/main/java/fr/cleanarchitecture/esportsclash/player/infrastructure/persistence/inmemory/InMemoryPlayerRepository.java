package fr.cleanarchitecture.esportsclash.player.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.core.infrastructure.persistence.inmemory.InMemoryBaseRepository;
import fr.cleanarchitecture.esportsclash.player.application.ports.PlayerRepository;
import fr.cleanarchitecture.esportsclash.player.domain.model.Player;

public class InMemoryPlayerRepository extends InMemoryBaseRepository<Player> implements PlayerRepository { }
