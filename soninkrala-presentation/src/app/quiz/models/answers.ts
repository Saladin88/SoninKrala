export interface UserAnswer {
  question: string
  answer : string
}

export interface AnswersChoice {
  answer : string
  creation_date? : Date
}

export interface CorrectAnswer {
  correctAnswer?: string | null
}

export interface AnswersChoices {
  questionId : number
  answer : string
}
