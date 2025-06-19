package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountJpaRepo extends JpaRepository<AccountEntity, Integer> {

    Optional<AccountEntity> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    Optional<AccountEntity> findByUuidToken(UUID uuid);

    void deleteAllByVerifyFalseAndCreationDateBefore(LocalDateTime date);

}
