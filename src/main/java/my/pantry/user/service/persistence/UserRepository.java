package my.pantry.user.service.persistence;

import my.pantry.user.service.domain.Account;
import my.pantry.user.service.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(@NotNull Long id);

    Optional<User> findByName(@NotBlank String name);

    User save(@NotBlank String name, @NotNull Account account);

    void deleteById(@NotNull Long id);
}
