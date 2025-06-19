import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, effect, inject } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { Subscription } from 'rxjs';
import { AlphabetService } from './service/alphabet.service';
import { LetterAudio } from './models/letter-audio-list';

@Component({
  selector: 'app-alphabet-audio-view',
  imports: [MatCardModule, CommonModule],
  templateUrl: './alphabet-audio-view.component.html',
  styleUrl: './alphabet-audio-view.component.css'
})
export class AlphabetAudioViewComponent implements OnInit, OnDestroy {

  datas! : LetterAudio[];
  private readonly subscriptions: Subscription[] = [];
  private readonly letterService = inject(AlphabetService);
  constructor() {
    effect(() => {
      this.datas = this.letterService.alphabetLettersSignal();
      console.log(this.datas)
    })
  }

  fetchAlphabet() {
    const getLetter = this.letterService.getAllAlphabetLetterWithAudio().subscribe({
      next : (data : LetterAudio[]) => {
        console.log(data)
        this.letterService.setAlphabetLetter(data || []);
      }
    })
    this.subscriptions.push(getLetter);
  }

  ngOnInit(): void {
    this.fetchAlphabet();
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }



}
