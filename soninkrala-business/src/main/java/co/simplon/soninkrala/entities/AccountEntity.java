package co.simplon.soninkrala.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="t_accounts")
public class AccountEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="email")
    private String email;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="is_verify")
    private boolean verify;

    @CreationTimestamp
    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="uuid_token")
    private UUID uuidToken;

    @Column(name="uuid_token_expiration")
    private LocalDateTime uuidTokenExpiration;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private RoleEntity role;

    public AccountEntity() {}

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;

    }

    public UUID getUuidToken() {
        return uuidToken;
    }

    public void setUuidToken(UUID uuidToken) {
        this.uuidToken = uuidToken;
    }

    public LocalDateTime getUuidTokenExpiration() {
        return uuidTokenExpiration;
    }

    public void setUuidTokenExpiration(LocalDateTime uuidTokenExpiration) {
        this.uuidTokenExpiration = uuidTokenExpiration;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]" + '\'' +
                ", verify=" + verify +
                '}';
    }
}
