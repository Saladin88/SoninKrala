import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {BodyPronunciation, ResultPronunciation} from '../Model/pronunciations';

@Injectable({
  providedIn: 'root'
})
export class PronunciationService {

  urlBaseBack = environment.backBaseUrl
  constructor(private readonly httpClient : HttpClient) { }

  sendVoicePronunciation(body : BodyPronunciation) : Observable<ResultPronunciation> {
    const url = this.urlBaseBack + '/pronunciations';
    return this.httpClient.post<ResultPronunciation>(url, body);
  }
}
