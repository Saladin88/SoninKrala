package co.simplon.soninkrala.controllers;


import co.simplon.soninkrala.dtos.UserAnswerDto;
import co.simplon.soninkrala.serviceimplements.QuizServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class QuizControllerTests {
    private static final QuizServiceImpl QUIZ_SERVICE = Mockito.mock(QuizServiceImpl.class);
    private static final MockMvc MOCK_MVC = MockMvcBuilders.standaloneSetup(new QuizController(QUIZ_SERVICE)).build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String QUESTION = "Question ? ";

    @Test
    public void getQuizData() throws Exception {
            MOCK_MVC.perform(get("/soninkrala/api/v1/quiz")).andExpect(status().isOk());
    }

    @Test
    public void retrieveCorrectAnswer() throws Exception {
        UserAnswerDto userAnswerDto = new UserAnswerDto(QUESTION, CORRECT_ANSWER);
        MOCK_MVC.perform(post("/soninkrala/api/v1/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userAnswerDto)))
                .andDo(print())
                .andExpect(status().isOk());

    }

}

