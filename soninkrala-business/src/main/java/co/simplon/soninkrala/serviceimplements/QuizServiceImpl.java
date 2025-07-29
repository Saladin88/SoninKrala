package co.simplon.soninkrala.serviceimplements;

import co.simplon.soninkrala.controllers.errors.FetchAllDataQuizErrorMessage;
import co.simplon.soninkrala.dtos.CorrectAnswer;
import co.simplon.soninkrala.dtos.QuestionQuiz;
import co.simplon.soninkrala.dtos.QuizDto;
import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.entities.AnswerEntity;
import co.simplon.soninkrala.entities.QuestionEntity;
import co.simplon.soninkrala.entities.QuizEntity;
import co.simplon.soninkrala.jpaRepositories.AnswerJpaRepo;
import co.simplon.soninkrala.jpaRepositories.QuestionJpaRepo;
import co.simplon.soninkrala.jpaRepositories.QuizJpaRepo;
import co.simplon.soninkrala.mappers.QuizMapper;
import co.simplon.soninkrala.services.QuizService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionJpaRepo questionJpaRepo;
    private final AnswerJpaRepo answerJpaRepo;
    private final QuizJpaRepo quizJpaRepo;

    public QuizServiceImpl(QuestionJpaRepo questionJpaRepo, AnswerJpaRepo answerJpaRepo, QuizJpaRepo quizJpaRepo) {
        this.questionJpaRepo = questionJpaRepo;
        this.answerJpaRepo = answerJpaRepo;
        this.quizJpaRepo = quizJpaRepo;
    }

    @Override
    public Set<QuestionQuiz> getAllQuestionsForQuizId(int id) {
        try {
            Set<QuestionEntity> questions = questionJpaRepo.findAllQUestionsGivenQuizId(id);
            return questions.stream().map(QuizMapper::toQuestionQuiz).collect(Collectors.toSet());
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

    @Override
    public Set<QuizDto> getAllQuiz() {
        List<QuizEntity> quizEntities= quizJpaRepo.findAll();
        return quizEntities.stream().map(QuizMapper::toQuizDto).collect(Collectors.toSet());
    }

    public boolean findQuestionId(Integer id) {
        return questionJpaRepo.findById(id).isPresent();
    }
}
