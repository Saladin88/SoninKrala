import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { ListQuestion } from '../models/list-question';
import { AnswersChoices, CorrectAnswer, UserAnswer } from '../models/answers';
import { environment } from '../../../environments/environment';
import { Question } from '../models/question';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private httpClient : HttpClient) { }

  private quizEndPointUri = environment.backBaseUrl;
  private questions = signal<Question[]>([]);
  private answers = signal<AnswersChoices[]>([]);

  getAllQuestionsData() : Observable<ListQuestion> {
    const url : string = this.quizEndPointUri + "/quiz";
    return this.httpClient.get<ListQuestion>(url);
  }

  postUserAnswer(body : AnswersChoices, id : number) {
    const url : string = this.quizEndPointUri + `/quiz/${id}/correct-answer`;
    return this.httpClient.post<CorrectAnswer>(url,body);
  }

  getAllAnswersByQuestionId(id : number | null) : Observable<AnswersChoices[]> {
    const url : string = this.quizEndPointUri + `/quiz/${id}/answers`;
    return this.httpClient.get<AnswersChoices[]>(url);
  }

  get questionList() {
    return this.questions;
  }

  setQuestionList(datas : Question[]) {
    return this.questions.set(datas ||[]);
  }
get answerList() {
  return this.answers;
}

setAnswersList(answers : AnswersChoices[]) {
  return this.answers.set(answers)
}

}
