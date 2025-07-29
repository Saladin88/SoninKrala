import { Component, OnDestroy, OnInit, effect, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { QuizService } from '../quiz/service/quiz.service';
import { Quiz } from '../quiz/models/list-question';
import {MatCardModule} from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-quiz-list-view',
  imports: [MatCardModule, CommonModule],
  templateUrl: './quiz-list-view.component.html',
  styleUrl: './quiz-list-view.component.css'
})
export class QuizListViewComponent implements OnInit, OnDestroy{

  private readonly subscriptions: Subscription[] = [];
  private readonly quizService = inject(QuizService);
  private readonly router = inject(Router)

  quizList! : Quiz[];

  constructor() {
    effect(() => {
      this.quizList = this.quizService.QuizList();
    })
  }

  ngOnInit(): void {
    this.fetchAllQuiz();
  }

  fetchAllQuiz() {
    const quizListSub = this.quizService.getAllQuiz().subscribe({
      next : (quizList : Quiz[]) => {
        console.log(quizList)
        this.quizService.setQuizList(quizList);
      },
      error : err => console.error(err)
    })
    this.subscriptions.push(quizListSub);
  }

  getQuestionsByQuizId(event: MouseEvent, quizId: number) {
    if(event.isTrusted) {
      this.router.navigate([`quiz/${quizId}/questions`])
    }

    }
    

  ngOnDestroy(): void {
   this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
