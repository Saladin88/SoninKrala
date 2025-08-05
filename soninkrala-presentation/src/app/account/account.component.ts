import { Component, OnDestroy, inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountService } from './service/account.service';
import {MatGridListModule} from '@angular/material/grid-list';
import { Tile } from './models/tile-grid';


@Component({
  selector: 'app-account',
  imports: [MatGridListModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnDestroy {

  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder)

  constructor(readonly accountService : AccountService){}

  tiles: Tile[] = [
    {text: 'Quiz', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Account info', cols: 1, rows: 3, color: 'lightgreen'},
    {text: 'Three', cols: 3, rows: 0.2, color: 'blue'},
    {text: 'Pronunciation', cols: 3, rows: 1, color: '#DDBDF1'},
  ];

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}

