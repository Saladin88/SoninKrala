package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

@Entity
@Table(name="t_words")
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private void setId(Integer id) {
        //generate by db
    }
    @Column(name = "word_label")
    private String wordLabel;

    @Column(name="language_code")
    private String languageCode;

    public WordEntity() {}

    public Integer getId() {
        return id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getWordLabel() {
        return wordLabel;
    }

    public void setWordLabel(String wordLabel) {
        this.wordLabel = wordLabel;
    }

    @Override
    public String toString() {
        return "WordEntity{" +
                "id=" + id +
                ", wordLabel='" + wordLabel + '\'' +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
