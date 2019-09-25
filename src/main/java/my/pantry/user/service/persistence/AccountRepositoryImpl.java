package my.pantry.user.service.persistence;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import my.pantry.user.service.domain.Account;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Singleton
public class AccountRepositoryImpl implements AccountRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public AccountRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Account.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByName(String name) {
        TypedQuery<Account> query = entityManager.createQuery("SELECT a FROM Account a WHERE a.name = :name", Account.class);
        query.setParameter("name", name);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    @Transactional
    public Account save(String name, String password) {
        Account Account = new Account(name, password);
        entityManager.persist(Account);
        return Account;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(Account -> entityManager.remove(Account));
    }
}
