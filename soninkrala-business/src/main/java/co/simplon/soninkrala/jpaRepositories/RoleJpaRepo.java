package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleJpaRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String member);
}
