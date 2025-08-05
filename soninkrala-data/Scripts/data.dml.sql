INSERT INTO t_roles (name, is_role_default) VALUES
                                             ('MEMBER', true),
                                             ('ADMIN', false);

insert into t_photos(picture_name,url_photo) VALUES
                                                  ('mais','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/mais.jpg'),
                                                  ('puit','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/puit.jpg'),
                                                  ('couteau','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/couteau.jpeg'),
                                                  ('poulet','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/poulet.jpeg'),
                                                  ('cheval','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/cheval.jpeg'),
                                                  ('chien','https://photos-associeted-to-words.s3.eu-west-3.amazonaws.com/chien.jpeg');

insert into t_questions (question,creation_date,id_photo) values
                                                     ('Quelle est la capitale du Mali ?','2024-09-05',1),
                                                     ('Que veut dire tohou ?', '2024-10-31',2),
                                                     ('Comment dit-on "Comment vas-tu ? "', '2025-02-10',3);


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



insert into t_letters (letter_name, id_audio_letter,letter_order, id_photo) values
                                                         ('A',1,1,1),
                                                         ('G', 2,7,2),
                                                         ('L',3,12,3),
                                                         ('Ŋ',4,16,4),
                                                         ('S',5,21,5),
                                                         ('W',6,24,6);
