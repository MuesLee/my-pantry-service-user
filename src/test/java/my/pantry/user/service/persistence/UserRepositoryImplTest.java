package my.pantry.user.service.persistence;

import io.micronaut.test.annotation.MicronautTest;
import my.pantry.user.service.domain.Account;
import my.pantry.user.service.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

@MicronautTest
public class UserRepositoryImplTest {

    @Inject
    UserRepository userRepository;

    @Inject
    AccountRepository accountRepository;

    @Test
    public void shouldCreateNewUser() {
        Account account = accountRepository.save("Werner", "123");

        User createdUser = userRepository.save("Hans", account);
        Assertions.assertThat(createdUser.getId()).isNotNull();
        Assertions.assertThat(createdUser.getAccount()).isEqualTo(account);
    }

    @Test
    public void shouldFindUserByName() {
        Account account = accountRepository.save("Derp", "123");
        userRepository.save("Derp", account);
        Optional<User> createdUser = userRepository.findByName("Derp");
        Assertions.assertThat(createdUser.isPresent()).isTrue();
    }

    @Test
    public void shouldThrowExceptionWithDuplicateUserNames() {
        Account account = accountRepository.save("Herp", "123");

        User createdUser1 = userRepository.save("Herp", account);
        Assertions.assertThat(createdUser1.getId()).isNotNull();
        User createdUser2 = userRepository.save("Herp", account);
        Assertions.assertThat(createdUser1.getId()).isEqualTo(createdUser2.getId());
    }

    @Test
    public void shouldDeleteUserAndAccountById() {
        Account account = accountRepository.save("Muck", "123");

        User createdUser = userRepository.save("Muck", account);
        Long createdUserId = createdUser.getId();

        Assertions.assertThat(createdUserId).isNotNull();

        Optional<User> user = userRepository.findById(createdUserId);
        Assertions.assertThat(user.isPresent()).isTrue();

        userRepository.deleteById(createdUserId);

        Optional<User> userAfterDeletion = userRepository.findById(createdUserId);
        Assertions.assertThat(userAfterDeletion.isPresent()).isFalse();

        Optional<Account> accountAfterDeletion = accountRepository.findById(createdUserId);
        Assertions.assertThat(accountAfterDeletion.isPresent()).isFalse();

    }
}
