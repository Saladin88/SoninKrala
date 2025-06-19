package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.LetterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterJpaRepo extends JpaRepository<LetterEntity, Integer> {

    List<LetterEntity> findAllByOrderByLetterOrderAsc();
}
