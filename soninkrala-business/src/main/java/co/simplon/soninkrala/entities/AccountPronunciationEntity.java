package co.simplon.soninkrala.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
@Entity
@Table(name = "t_account_pronunciation_attempts")
public class AccountPronunciationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name="similarity_score", nullable = false)
    private double similarityScore;

    @CreationTimestamp
    @Column(name="attempted_at", nullable = false)
    private OffsetDateTime attemptedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn (name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn (name = "word_id", nullable = false)
    private WordEntity word;

    private void setId(Integer id) {
        //generated db
    }

    public Integer getId() {
        return id;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public OffsetDateTime getAttemptedAt() {
        return attemptedAt;
    }

    public void setAttemptedAt(OffsetDateTime attemptedAt) {
        this.attemptedAt = attemptedAt;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public WordEntity getWord() {
        return word;
    }

    public void setWord(WordEntity word) {
        this.word = word;
    }
}
