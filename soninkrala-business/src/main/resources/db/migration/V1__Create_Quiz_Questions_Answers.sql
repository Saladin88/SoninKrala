BEGIN;

DROP TABLE IF EXISTS t_questions, t_answers;

CREATE TABLE t_questions (
                             id INT GENERATED ALWAYS AS IDENTITY,
                             question VARCHAR(80) NOT NULL,
                             creation_date DATE,
                             CONSTRAINT t_questions_pkey PRIMARY KEY (id)

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

CREATE TABLE t_accounts (
                            id INT GENERATED ALWAYS AS IDENTITY,
                            firstname varchar(50) NOT NULL,
                            lastname varchar(50) NOT NULL,
                            email varchar(100) NOT NULL,
                            username varchar(20) NOT NULL,
                            password varchar(80) NOT NULL,
                            valid BOOLEAN NOT NULL,
                            CONSTRAINT t_accounts_pkey PRIMARY KEY (id),
                            CONSTRAINT t_accounts_email_ukey UNIQUE (email),
                            CONSTRAINT t_accounts_username_ukey UNIQUE (username)
);

insert into t_questions (question,creation_date) values
                                                     ('Quelle est la capitale du Mali ?','2024-09-05'),
                                                     ('Que veut dire tohou ?', '2024-10-31'),
                                                     ('Comment dit-on "Comment vas-tu ? "', '2025-02-10');


insert into t_answers (answer, is_correct_answer, id_question) values
                                                                   ('Bamako', true, 1),
                                                                   ('Segou', false, 1),
                                                                   ('Kayes', false, 1),
                                                                   ('Senou', false, 1),
                                                                   ('sandales/chaussures', true, 2),
                                                                   ('eau', false, 2),
                                                                   ('toto', false, 2),
                                                                   ('beau/belle', false, 2),
                                                                   ('àan djamou', false, 3),
                                                                   ('dji', false, 3),
                                                                   ('àan moroh', true, 3),
                                                                   ('nouhary', false, 3);


END;
