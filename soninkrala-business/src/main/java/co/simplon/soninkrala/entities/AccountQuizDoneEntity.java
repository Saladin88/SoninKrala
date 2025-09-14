package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

@Entity
@Table(name="t_quiz_done")
public class AccountQuizDoneEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
