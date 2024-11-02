package fr.cleanarchitecture.esportsclash.core.domain.application.ports;

import java.util.Optional;

public interface BaseRepository<T> {

    Optional<T> findById(String id);

    void save(T entity);

    void delete(T entity);

    void clear();
}
