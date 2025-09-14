export interface BodyPronunciation {
  audio : File
  word : string
}
export interface ResultPronunciation {
  id? : number
  score : number
  audioName : string
  creationDate? : string
}

export interface Word {
  id: number
  wordCode : string
  wordLabel : string
}
