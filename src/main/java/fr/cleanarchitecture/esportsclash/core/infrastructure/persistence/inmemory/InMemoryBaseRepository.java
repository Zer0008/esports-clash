package fr.cleanarchitecture.esportsclash.core.infrastructure.persistence.inmemory;

import fr.cleanarchitecture.esportsclash.core.domain.application.ports.BaseRepository;
import fr.cleanarchitecture.esportsclash.core.domain.model.BaseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    private Map<String, T> entities = new HashMap<>();

    public Optional<T> findById(String id) {
        return Optional.ofNullable(entities.get(id));
    }

    public void save(T entity) {
        this.entities.put(entity.getId(), entity);
    }

    public void delete(T entity) {
        this.entities.remove(entity.getId());
    }
}
