package fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SQLUserDataAccessor extends JpaRepository<SQLUser, String> {
    Optional<SQLUser> findByEmailAddress(String email);
}
