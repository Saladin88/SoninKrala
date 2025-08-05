DROP TABLE IF EXISTS t_questions, t_answers, t_accounts, t_roles, t_audios_letters, t_letters, t_photos, t_quiz, t_questions_t_quiz CASCADE ;

CREATE TABLE t_photos (
                          id int GENERATED ALWAYS AS IDENTITY,
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

CREATE TABLE t_accounts (
                            id INT GENERATED ALWAYS AS IDENTITY,
                            firstname varchar(50) NOT NULL,
                            lastname varchar(80) NOT NULL,
                            email varchar(100) NOT NULL,
                            username varchar(20) NOT NULL,
                            password varchar(80) NOT NULL,
                            is_verify BOOLEAN NOT NULL,
                            creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            uuid_token UUID,
                            uuid_token_expiration TIMESTAMP,
                            id_role INT NOT NULL,
                            CONSTRAINT t_accounts_pkey PRIMARY KEY (id),
                            CONSTRAINT t_accounts_email_ukey UNIQUE (email),
                            CONSTRAINT t_accounts_username_ukey UNIQUE (username),
                            CONSTRAINT t_accounts_t_roles_fkey FOREIGN KEY (id_role)
                                REFERENCES t_roles (id)
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
