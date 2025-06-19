package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.entities.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerJpaRepo extends JpaRepository<AnswerEntity, Integer> {

    AnswerEntity findByCorrectAnswerTrueIgnoreCaseAndIdQuestion_Id(int idQuestion);

    List<AnswerEntity> getAllByIdQuestion_Id(int idQuestion);
}
