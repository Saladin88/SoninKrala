import { quizAttempt } from "../../quiz/models/list-question"
import { ResultPronunciation } from "../../record-audio/Model/pronunciations"

export interface AccountGeneralInfo {
    firstname: string
    lastname: string
    username: string
    profilePictureUrl: string
    email: string
    role: string
    pronunciationResults: ResultPronunciation[]
    quizAttemptsList: quizAttempt[]
}