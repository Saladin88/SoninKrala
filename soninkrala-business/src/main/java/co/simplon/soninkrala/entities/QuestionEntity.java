package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "id_photo")
    private PhotoEntity photo;

    public QuestionEntity() {
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        //generated by db
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

    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
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
