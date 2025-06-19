package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="t_questions")
public class QuestionEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="question")
    private String question;

    @Column(name="creation_date")
    private OffsetDateTime creationDate;


    public QuestionEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }



    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", questionId='" + question + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

}
