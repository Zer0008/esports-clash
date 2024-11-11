package fr.cleanarchitecture.esportsclash.team.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.core.infrastructure.persistence.inmemory.InMemoryBaseRepository;
import fr.cleanarchitecture.esportsclash.team.application.ports.TeamRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;

import java.util.Optional;

public class InMemoryTeamRepository extends InMemoryBaseRepository<Team> implements TeamRepository {
    @Override
    public Optional<Team> findByName(String name) {
        return entities.values().stream().filter(team -> team.getTeamName().equals(name)).findFirst();
    }
}
