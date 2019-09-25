package my.pantry.user.service.persistence;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import my.pantry.user.service.domain.Account;
import my.pantry.user.service.domain.User;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Singleton
public class UserRepositoryImpl implements UserRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("SELECT a FROM User a WHERE a.name = :name", User.class);
        query.setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public User save(String name, Account account) {
        User User = new User(name, account);
        entityManager.persist(User);
        return User;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(User -> entityManager.remove(User));
    }
}
