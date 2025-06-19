delete from t_answers ;
DELETE FROM t_questions;

insert into t_questions (question,creation_date) values 
('Quelle est la capitale du Mali ?','2024-09-05'),
('Que veut dire tohou ?', '2024-10-31');


insert into t_answers (answer, is_correct_answer, id_question) values 
('Bamako', true, 5),
('Segou', false, 5),
('Kayes', false, 5),
('Senou', false, 5),
('sandales/chaussures', true, 6),
('eau', false, 6),
('toto', false, 6),
('beau/belle', false, 6);

select * from t_questions tq ;
select * from t_answers ta ;
