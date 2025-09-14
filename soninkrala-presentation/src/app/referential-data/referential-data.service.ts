import { Injectable, signal } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Word } from '../record-audio/Model/pronunciations';
import { TermPolicy } from './models/referential-data-response';

@Injectable({
  providedIn: 'root'
})
export class ReferentialDataService {
  
  private words = signal<Word[]>([]);
  private termVersion = signal<TermPolicy | null>(null);
  urlBaseBack = environment.backBaseUrl
  constructor(private readonly httpClient : HttpClient) { }

  fetchAllWord() : Observable<Word[]> {
    const url = this.urlBaseBack + '/words';
    return this.httpClient.get<Word[]>(url);
  }
  fetchTermVersion() : Observable<TermPolicy>{
    const url = this.urlBaseBack + '/referential-data/term-and-policy-versions';
    return this.httpClient.get<TermPolicy>(url);
  }

  get wordList() {
    return this.words;
  }
  get version() {
    return this.termVersion.asReadonly();
  }
  setVersion(data : TermPolicy) {
    this.termVersion.set(data);
  }
}
