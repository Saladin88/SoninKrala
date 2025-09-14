package co.simplon.soninkrala.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="t_term_versions")
public class TermVersionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "version")
    private String version;

    @Column(name = "published_at")
    private OffsetDateTime publishedAt;

    @Column(name="label_version")
    private String labelVersion;


    private void setId(Integer id) {
        //generate by db
    }

    public Integer getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(OffsetDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getLabelVersion() {
        return labelVersion;
    }

    public void setLabelVersion(String labelVersion) {
        this.labelVersion = labelVersion;
    }
}