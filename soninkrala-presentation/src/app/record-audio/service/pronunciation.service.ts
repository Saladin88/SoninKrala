import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import { ResultPronunciation} from '../Model/pronunciations';

@Injectable({
  providedIn: 'root'
})
export class PronunciationService {

  urlBaseBack = environment.backBaseUrl
  constructor(private readonly httpClient : HttpClient) { }

  sendVoicePronunciation(word : string, body : FormData) : Observable<ResultPronunciation> {
    const url = this.urlBaseBack + `/pronunciations/${word}/word`;
    console.log('body = ', body);
    return this.httpClient.post<ResultPronunciation>(url, body);
  }
}

