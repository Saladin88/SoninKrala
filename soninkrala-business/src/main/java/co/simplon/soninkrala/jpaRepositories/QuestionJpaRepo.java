package co.simplon.soninkrala.jpaRepositories;

import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionJpaRepo extends JpaRepository<QuestionEntity, Integer> {
    //Les repo travaillent avec les entités. IL ne faut pas utiliser de DTO dedans  !!!!!!!!!

//    @Query(value="select answer from t_answers left join t_questions on t_questions.id = t_answers.id_question where t_answers.id_question = :id and t_answers.is_correct_answer = true", nativeQuery = true)


//    @Query("select new co.simplon.soninkrala.dtos.CorrectAnswer(a.answer) from AnswerEntity a where a.idQuestion.id = :id and a.correctAnswer = true")//JPQL
//    CorrectAnswer findCorrectAnswerWithIdQuestion(@Param("id") Integer id);
}
