DROP TABLE IF EXISTS t_questions, t_answers, t_accounts, t_roles, t_audios_letters, t_letters, t_photos, t_quiz, t_questions_t_quiz,t_pronunciation_attempts,t_term_versions,t_accounts_t_quiz_attempts CASCADE ;

CREATE TABLE t_pronunciation_attempts (
                        id INT GENERATED ALWAYS AS IDENTITY,
                        pronunciation_word VARCHAR(50),
                        attempted_at DATETIME,
                        similarity_score DECIMAL(5,2) NOT NULL,
                        CONSTRAINT t_pronunciation_attempts_pkey PRIMARY KEY(id),
                        CONSTRAINT t_pronunciation_attempts_ukey UNIQUE (pronunciation_word,attempted_at)
);


CREATE TABLE t_photos (
                          id INT GENERATED ALWAYS AS IDENTITY,
                          picture_name varchar(120) NOT NULL,
                          url_photo varchar(200) NOT NULL,
                          CONSTRAINT t_photos_pkey PRIMARY KEY (id),
                          CONSTRAINT t_photos_ukey UNIQUE (picture_name,url_photo)
);

CREATE TABLE t_quiz (
                        id INT GENERATED ALWAYS AS IDENTITY,
                        quiz_name VARCHAR(90) NOT NULL,
                        description TEXT NOT NULL,
                        CONSTRAINT t_quiz_pkey PRIMARY KEY (id),
                        CONSTRAINT t_quiz_name_ukey UNIQUE (quiz_name)

);

CREATE TABLE t_questions (
                             id INT GENERATED ALWAYS AS IDENTITY,
                             question VARCHAR(80) NOT NULL,
                             creation_date DATE,
                             id_photo INT NOT NULL,
                             CONSTRAINT t_questions_pkey PRIMARY KEY (id),
                             CONSTRAINT t_questions_t_quiz_ukey UNIQUE (question),
                             CONSTRAINT t_questions_t_photos_fkey FOREIGN KEY (id_photo)
                                REFERENCES  t_photos(id)
);

CREATE TABLE t_questions_t_quiz (
                            id_quiz INT,
                            id_question INT,
                            CONSTRAINT t_questions_t_quiz_pkey PRIMARY KEY (id_quiz, id_question),
                            CONSTRAINT t_questions_t_quiz_fkey FOREIGN KEY (id_quiz)
                                REFERENCES t_quiz(id),
                            CONSTRAINT t_questions_t_question_fkey FOREIGN KEY (id_question)
                                REFERENCES t_questions(id)
);

CREATE TABLE t_answers (
                           id INT GENERATED ALWAYS AS IDENTITY,
                           answer VARCHAR(50) NOT NULL,
                           creation_date DATE,
                           is_correct_answer BOOLEAN NOT NULL,
                           id_question INT NOT NULL,
                           CONSTRAINT t_answers_pkey PRIMARY KEY (id),
                           CONSTRAINT t_answers_t_questions_fkey FOREIGN KEY (id_question)
                               REFERENCES t_questions(id)
);

CREATE TABLE t_roles (
                         id int GENERATED ALWAYS AS IDENTITY,
                         name varchar(10),
                         is_role_default boolean,
                         CONSTRAINT t_role_pkey PRIMARY KEY (id),
                         CONSTRAINT t_role_name_ukey UNIQUE (name)
);

CREATE TABLE t_term_versions (
                          id int GENERATED ALWAYS AS IDENTITY,
                          version VARCHAR(20) NOT NULL,
                          published_at DATETIME NOT NULL,
                          label_version VARCHAR(100),
                          CONSTRAINT t_term_versions_pkey PRIMARY KEY(id),
                          CONSTRAINT t_term_versions_ukey UNIQUE (version)
);

CREATE TABLE t_accounts (
                            id INT GENERATED ALWAYS AS IDENTITY,
                            firstname varchar(50) NOT NULL,
                            lastname varchar(80) NOT NULL,
                            email varchar(100) NOT NULL,
                            username varchar(20) NOT NULL,
                            password varchar(80) NOT NULL,
                            profile_image varchar(200),
                            is_verify BOOLEAN NOT NULL,
                            rgpd_accepted_at DATETIME NOT NULL,
                            creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            uuid_token UUID,
                            uuid_token_expiration TIMESTAMP,
                            id_role INT NOT NULL,
                            id_rgpd_version NOT NULL,
                            CONSTRAINT t_accounts_pkey PRIMARY KEY (id),
                            CONSTRAINT t_accounts_email_ukey UNIQUE (email),
                            CONSTRAINT t_accounts_username_ukey UNIQUE (username),
                            CONSTRAINT t_accounts_t_roles_fkey FOREIGN KEY (id_role)
                                REFERENCES t_roles (id),
                            CONSTRAINT t_accounts_t_term_versions_fkey FOREIGN KEY (id_rgpd_version)
                                REFERENCES t_term_versions (id)
);

CREATE TABLE t_accounts_t_quiz_attempts (
                           id_account INT,
                           id_quiz INT,
                           score INT NOT NULL,
                           attempt_number INT,
                           completed_at DATETIME NOT NULL,
                           CONSTRAINT t_accounts_t_quiz_attempts_pkey PRIMARY KEY(id_account, id_quiz),
                           CONSTRAINT t_accounts_t_quiz_attempts_ukey UNIQUE(id_account, id_quiz,completed_at),
                           CONSTRAINT t_accounts_t_quiz_attempts_t_accounts_fkey FOREIGN KEY(id_account) REFERENCES t_accounts(id),
                           CONSTRAINT t_accounts_t_quiz_attempts_t_quiz_attempts_fkey FOREIGN KEY(id_quiz) REFERENCES t_quiz_attempts(id)
);

CREATE TABLE t_accounts_t_pronunciation_attempts (
                        id_account INT,
                        id_pronunciation INT,
                        attempted_at DATETIME,
                        similarity_score DECIMAL(5,2) NOT NULL,
                        CONSTRAINT t_accounts_t_pronunciation_attempts_pkey PRIMARY KEY(id_account,id_pronunciation),
                        CONSTRAINT t_accounts_t_pronunciation_attempts_ukey UNIQUE(id_account, id_pronunciation,attempted_at),
                        CONSTRAINT t_accounts_t_quiz_attempts_t_accounts_fkey FOREIGN KEY(id_account) REFERENCES t_accounts(id),
                        CONSTRAINT t_accounts_t_quiz_attempts_t_pronunciation_attempts_fkey FOREIGN KEY(id_pronunciation) REFERENCES t_pronunciation_attempts(id)
);


CREATE TABLE t_audios_letters (

                            id int GENERATED ALWAYS AS IDENTITY,
                            url_link varchar(200) NOT NULL,
                            CONSTRAINT t_audios_letters_pkey PRIMARY KEY (id),
                            CONSTRAINT t_audios_letters_ukey UNIQUE (url_link)

);


CREATE TABLE t_letters (
                           id int GENERATED ALWAYS AS IDENTITY,
                           letter_name varchar(5) NOT NULL,
                           letter_order int NOT NULL UNIQUE,
                           id_audio_letter INT NOT NULL,
                           id_photo INT NOT NULL,
                           CONSTRAINT t_letters_pkey PRIMARY KEY (id),
                           CONSTRAINT t_letters_ukey UNIQUE (letter_name),
                           CONSTRAINT t_letters_t_audios_letters_fkey FOREIGN KEY (id_audio_letter)
                               REFERENCES t_audios_letters(id),
                           CONSTRAINT t_letters_t_photos_fkey FOREIGN KEY (id_photo)
                               REFERENCES  t_photos(id)

);
