package my.pantry.user.service.persistence;

import io.micronaut.test.annotation.MicronautTest;
import my.pantry.user.service.domain.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

@MicronautTest
public class AccountRepositoryImplTest {

    @Inject
    AccountRepository accountRepository;

    @Test
    public void shouldCreateNewAccount() {
        Account createdAccount = accountRepository.save("Hans", "password");
        Assertions.assertThat(createdAccount.getId()).isNotNull();
        Assertions.assertThat(createdAccount.getUser()).isNull();
    }

    @Test
    public void shouldFindAccountByName() {
        accountRepository.save("Dampf", "password");
        Optional<Account> createdAccount = accountRepository.findByName("Dampf");
        Assertions.assertThat(createdAccount.isPresent()).isTrue();
    }

    @Test
    public void shouldThrowExceptionWithDuplicateAccountNames() {
        Account createdAccount1 = accountRepository.save("Peter", "password");
        Assertions.assertThat(createdAccount1.getId()).isNotNull();
        Account createdAccount2 = accountRepository.save("Peter", "password");
        Assertions.assertThat(createdAccount1.getId()).isEqualTo(createdAccount2.getId());
    }

    @Test
    public void shouldDeleteAccountById() {
        Account createdAccount = accountRepository.save("Ursula", "password");
        Long createdAccountId = createdAccount.getId();

        Assertions.assertThat(createdAccountId).isNotNull();

        Optional<Account> account = accountRepository.findById(createdAccountId);
        Assertions.assertThat(account.isPresent()).isTrue();

        accountRepository.deleteById(createdAccountId);

        Optional<Account> accountAfterDeletion = accountRepository.findById(createdAccountId);
        Assertions.assertThat(accountAfterDeletion.isPresent()).isFalse();
    }
}
