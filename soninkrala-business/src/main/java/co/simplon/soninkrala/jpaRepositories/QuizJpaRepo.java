package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.entities.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizJpaRepo extends JpaRepository<QuizEntity,Integer> {
}
