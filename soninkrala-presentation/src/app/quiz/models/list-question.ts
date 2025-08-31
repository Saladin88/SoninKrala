
export interface Quiz {
  quizId : number
  quizName : string
  description : string
}

export interface quizAttempt {
  quizId : number
  quizName : string
  description : string
  attemptNumber: number
  attemptDate: string
  score : number
}
