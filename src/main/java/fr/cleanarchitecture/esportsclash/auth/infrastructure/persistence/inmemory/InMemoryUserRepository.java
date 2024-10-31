package fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;
import fr.cleanarchitecture.esportsclash.core.infrastructure.persistence.inmemory.InMemoryBaseRepository;

public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    @Override
    public boolean emailAddressAvailable(String userEmailAddress) {
        return entities.values()
                .stream()
                .noneMatch(user -> user.getUserEmailAddress().equals(userEmailAddress));
    }
}
