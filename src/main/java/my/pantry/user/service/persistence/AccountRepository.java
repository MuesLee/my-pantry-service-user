package my.pantry.user.service.persistence;

import my.pantry.user.service.domain.Account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(@NotNull Long id);

    Optional<Account> findByName(@NotBlank String name);

    Account save(@NotBlank String name, @NotBlank String password);

    void deleteById(@NotNull Long id);
}
