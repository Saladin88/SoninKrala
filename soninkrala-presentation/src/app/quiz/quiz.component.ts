import { Component, OnDestroy, OnInit, ViewChild, ElementRef, effect, inject } from '@angular/core';
import { QuizService } from './service/quiz.service';
import { Question } from './models/question';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { AnswersChoices, CorrectAnswer, UserAnswer } from './models/answers';
import { Subscription } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-quiz',
  imports: [MatCardModule, CommonModule, MatButtonModule,MatProgressSpinnerModule],
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit, OnDestroy {

questions : Question[] = [];
currentQuestion! : Question | null;
answersProp! : AnswersChoices[];
currentQuestionIndex = -1;
correctAnswer! : CorrectAnswer | null;
selectedAnswer : string | null = null;
isAnswerBlocked : boolean = false;
private readonly subscriptions: Subscription[] = [];
private readonly route = inject(ActivatedRoute);


  constructor( readonly quizService: QuizService) {
    effect(() => {
      this.questions = this.quizService.questionList();
      this.answersProp = this.quizService.answerList();
      console.log(this.questions)
    })
  }

  fetchQuizData() {
    const quizId : number = this.route.snapshot.params['id'];
    this.quizService.getAllQuestionsGivenQuizId(quizId).subscribe({
      next : (datas : Question[]) => {
        this.quizService.setQuestionList(datas);
      },
      error : err => console.error(err)
    }
    );
  }

  selectAnswer(answer: AnswersChoices): void {
    if(!this.isAnswerBlocked) {
      this.selectedAnswer = answer.answer;
    }
  }

  isCorrectAnswer(answer: AnswersChoices): boolean {
    // Ne rien afficher tant que la réponse n’a pas été validée
    if (!this.isAnswerBlocked) return false;

    // Cas 1 : la réponse est correcte → correctAnswer est null
    if (this.correctAnswer === null) {
      return this.selectedAnswer === answer.answer;
    }

    // Cas 2 : la réponse est incorrecte → correctAnswer contient la bonne réponse
    return this.correctAnswer.correctAnswer === answer.answer;
  }



  isWrongAnswer(answer: AnswersChoices): boolean {
    // Si la réponse est bloquée (donc validée)
    // et que l'utilisateur a sélectionné cette réponse
    // et que ce n'est pas la bonne
    if (!this.isAnswerBlocked || !this.selectedAnswer) return false;

    const isSelected = this.selectedAnswer === answer.answer;

    // Cas 1 : la réponse est correcte → correctAnswer est null
    if (this.correctAnswer === null) {
      return false; // pas de mauvaise réponse
    }

    // Cas 2 : la réponse est incorrecte → compare la sélection à la bonne réponse
    return isSelected && this.correctAnswer.correctAnswer !== answer.answer;
  }

  ngOnInit() {
    this.fetchQuizData();

  }
  quizStart() : void {
    this.currentQuestionIndex = 0;
    this.loadQuestion();
  }

  loadQuestion() {
    this.currentQuestion= this.questions[this.currentQuestionIndex];
    if(this.currentQuestion) {
      this.fetchPossibleAnswers();
    }
  }

  fetchPossibleAnswers() {
    const getAnswersForm = this.quizService.getAllAnswersByQuestionId(this.currentQuestion?.id || null).subscribe({
      next : (answers : AnswersChoices[]) => {
        console.log(answers);
        return this.quizService.setAnswersList(answers);
      },
      error : (err) => console.error(err)
    })
    this.subscriptions.push(getAnswersForm);
  }

  nextQuestion(event : MouseEvent) {
    if(event.isTrusted) {
      this.correctAnswer = null;
      this.currentQuestionIndex++;
      if(this.currentQuestionIndex < this.questions.length) {
        this.loadQuestion();
      } else {
        this.currentQuestion = null;
        this.answersProp = [];
      }
      this.selectedAnswer = null;
      this.isAnswerBlocked = false;
    }
  }

  convertToUserAnswer(id : number, input : string) : AnswersChoices {
    const userAnswer : AnswersChoices = {
      questionId : id,
      answer : input
    }
    return userAnswer;
  }

  getCorrectAnswer(event : MouseEvent, answer : AnswersChoices) {
    if(event.isTrusted) {
      const correctAnswerForm = this.quizService.postUserAnswer(answer, answer.questionId).subscribe({
        next : (answer : CorrectAnswer) => {
          this.correctAnswer = answer;
            this.isAnswerBlocked = true;
          console.log(answer)
        },
        error : (err) => console.error(err)
      })
      this.subscriptions.push(correctAnswerForm);
    }
  }


  onSubmit(event: Event) {
    event.preventDefault();

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
   }


}
