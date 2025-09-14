import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { Subscription } from 'rxjs';
import { ReferentialDataService } from '../../referential-data/referential-data.service';
import { Word } from '../Model/pronunciations';

@Component({
  selector: 'app-word-list-view',
  imports: [],
  templateUrl: './word-list-view.component.html',
  styleUrl: './word-list-view.component.css'
})
export class WordListViewComponent implements OnInit, OnDestroy {

private readonly referentialDataService = inject(ReferentialDataService)

  private readonly subscriptions: Subscription[] = [];

  ngOnInit(): void {
    this.fetchAllWord()
  }
  fetchAllWord() {
    this.referentialDataService.fetchAllWord().subscribe({
      next : (data : Word[]) => {
        console.log(data)
        
      }
    })
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
