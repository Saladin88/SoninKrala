package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.controllers.errors.FetchAllDataQuizErrorMessage;
import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuizData;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.entities.AnswerEntity;
import co.simplon.soninkrala.entities.QuestionEntity;
import co.simplon.soninkrala.jpaRepositories.AnswerJpaRepo;
import co.simplon.soninkrala.jpaRepositories.QuestionJpaRepo;
import co.simplon.soninkrala.mappers.QuizMapper;
import co.simplon.soninkrala.services.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionJpaRepo questionJpaRepo;
    private final AnswerJpaRepo answerJpaRepo;

    public QuizServiceImpl(QuestionJpaRepo questionJpaRepo, AnswerJpaRepo answerJpaRepo) {
        this.questionJpaRepo = questionJpaRepo;
        this.answerJpaRepo = answerJpaRepo;
    }

    @Override
    public QuizData getAll() {
        try {
            List<QuestionEntity> questions = questionJpaRepo.findAll();
            return new QuizData(QuizMapper.fromEntitiesToDtoList(questions));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FetchAllDataQuizErrorMessage("An error occurred while fetching data" , e);
        }
    }

    @Override
    public CorrectAnswer getCorrectAnswer(int id, UserAnswerDto userAnswer) {
        AnswerEntity answerEntity = answerJpaRepo.findByCorrectAnswerTrueIgnoreCaseAndIdQuestion_Id(id);
        if(answerEntity.getAnswer().equals(userAnswer.answer())) {
            System.out.println("this is the id :  " + id + " this is the correct answer : " +  answerEntity.getAnswer());
            return null;
        }
        return new CorrectAnswer(answerEntity.getAnswer());
    }

    @Override
    public List<UserAnswerDto> retrieveAllAnswers(int id) {
        try {
            List<AnswerEntity> answers = answerJpaRepo.getAllByIdQuestion_Id(id);
            return QuizMapper.toAnswerEntities(answers);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FetchAllDataQuizErrorMessage("An error occurred while fetching data" , e);
        }
    }

    public boolean findQuestionId(Integer id) {
        return questionJpaRepo.findById(id).isPresent();
    }
}
