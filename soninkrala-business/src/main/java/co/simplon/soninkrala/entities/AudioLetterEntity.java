package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

@Entity
@Table(name="t_audios_letters")
public class AudioLetterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="url_link")
    private String urlLink;

    public AudioLetterEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
}
