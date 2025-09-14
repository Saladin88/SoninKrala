package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordJpaRepo extends JpaRepository<WordEntity,Integer> {
    Optional<WordEntity> findByWordLabel(String word);
}
