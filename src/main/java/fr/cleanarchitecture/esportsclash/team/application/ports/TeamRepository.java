package fr.cleanarchitecture.esportsclash.team.application.ports;

import fr.cleanarchitecture.esportsclash.core.domain.application.ports.BaseRepository;
import fr.cleanarchitecture.esportsclash.team.domain.model.Team;

import java.util.Optional;

public interface TeamRepository extends BaseRepository<Team> {
    Optional<Team> findByName(String name);
}
