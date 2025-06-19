import { Component, OnDestroy, inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountService } from './service/account.service';

@Component({
  selector: 'app-account',
  imports: [],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnDestroy {

  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder)

  constructor(readonly accountService : AccountService){}

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}

