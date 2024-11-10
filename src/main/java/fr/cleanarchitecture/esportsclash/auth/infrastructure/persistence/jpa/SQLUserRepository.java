package fr.cleanarchitecture.esportsclash.auth.infrastructure.persistence.jpa;

import fr.cleanarchitecture.esportsclash.auth.application.ports.UserRepository;
import fr.cleanarchitecture.esportsclash.auth.domain.model.User;

import java.util.Optional;

public class SQLUserRepository implements UserRepository {
    private final SQLUserDataAccessor sqlUserDataAccessor;

    public SQLUserRepository(SQLUserDataAccessor sqlUserDataAccessor) {
        this.sqlUserDataAccessor = sqlUserDataAccessor;
    }

    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        var sqlUser = sqlUserDataAccessor.findByEmailAddress(emailAddress);
        return sqlUser.map(user ->
                new User(
                    user.getId(),
                    user.getEmailAddress(),
                    user.getPassword()
                )
        );
    }

    @Override
    public Optional<User> findById(String id) {
        var sqlUser = sqlUserDataAccessor.findById(id);
        return sqlUser.map(user ->
                new User(
                        user.getId(),
                        user.getEmailAddress(),
                        user.getPassword()
                )
        );
    }

    @Override
    public void save(User user) {
        var sqlUser = new SQLUser(
                user.getIdUser(),
                user.getUserEmailAddress(),
                user.getPassword()
        );
        sqlUserDataAccessor.save(sqlUser);
    }

    @Override
    public void delete(User user) {
        var sqlUser = new SQLUser(
                user.getId(),
                user.getUserEmailAddress(),
                user.getPassword()
        );
        sqlUserDataAccessor.delete(sqlUser);
    }

    @Override
    public void clear() {
        sqlUserDataAccessor.deleteAll();
    }
}
