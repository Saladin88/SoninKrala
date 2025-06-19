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
id_question INT,
CONSTRAINT t_answers_pkey PRIMARY KEY (id),
CONSTRAINT t_answers_t_questions_fkey FOREIGN KEY (id_question)
	REFERENCES t_questions(id)
);

select * from t_questions tq;

select * from t_answers ta;