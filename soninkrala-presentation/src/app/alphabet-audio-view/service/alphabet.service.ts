import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { LetterAudio } from '../models/letter-audio-list';

@Injectable({
  providedIn: 'root'
})
export class AlphabetService {

  private alphabetLetters = signal<LetterAudio[]>([]);

  private accountEndPointUri = environment.backBaseUrl;

  constructor( readonly httpClient : HttpClient) { }

  getAllAlphabetLetterWithAudio() : Observable<LetterAudio[]> {
    const url : string = this.accountEndPointUri + "/alphabet";
    return this.httpClient.get<LetterAudio[]>(url);
  }

  get alphabetLettersSignal() {
    console.log("this is alphabet letters ==" , this.alphabetLetters)
    return this.alphabetLetters;
  }

  setAlphabetLetter( data : LetterAudio[]) {
    this.alphabetLetters.set(data || []);
  }
}
