BEGIN;

DROP TABLE IF EXISTS t_questions, t_answers, t_accounts, t_roles, t_audios_letters, t_letters, t_photos CASCADE ;

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
                            password varchar(20) NOT NULL,
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

CREATE TABLE t_photos (
                          id int GENERATED ALWAYS AS IDENTITY,
                          picture_name varchar(120) NOT NULL,
                          url_photo varchar(200) NOT NULL,
                          CONSTRAINT t_photos_pkey PRIMARY KEY (id),
                          CONSTRAINT t_photos_ukey UNIQUE (picture_name,url_photo)
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


INSERT INTO t_roles (name, role_default) VALUES
                                             ('MEMBER', true),
                                             ('ADMIN', false);

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

insert into t_audios_letters (url_link) values
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/A-Makka-Mais.mp3'),
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/G-gede-un-puit.mp3'),
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/L-labo-un-couteau.mp3'),
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/Nje-selinje-un-poulet.mp3'),
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/S-si-un-cheval.mp3'),
                                            ('https://audios-alphabet.s3.eu-west-3.amazonaws.com/W-wule-un-chien.mp3');

insert into t_photos(picture_name,url_photo) VALUES
                                                  ('mais','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/mais.jpg'),
                                                  ('puit','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/puit.jpg'),
                                                  ('couteau','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/couteau.jpeg'),
                                                  ('poulet','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/poulet.jpeg'),
                                                  ('cheval','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/cheval.jpeg'),
                                                  ('chien','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/chien.jpeg');


insert into t_letters (letter_name, id_audio_letter,letter_order, id_photo) values
                                                         ('A',1,1,1),
                                                         ('G', 2,7,2),
                                                         ('L',3,12,3),
                                                         ('Ŋ',4,16,4),
                                                         ('S',5,21,5),
                                                         ('W',6,24,6);
END;
