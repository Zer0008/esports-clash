package fr.cleanarchitecture.esportsclash.auth.application.ports;

import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.core.domain.application.ports.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {
        boolean emailAddressAvailable(String userEmailAddress);
        Optional<User> findByEmailAddress(String emailAddress);
}
