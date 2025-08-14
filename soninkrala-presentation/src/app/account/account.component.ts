import { Component, OnDestroy, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountService } from './service/account.service';
import {MatGridListModule} from '@angular/material/grid-list';
import { Tile } from './models/tile-grid';
import { CustomValidators } from '../Validators/whiteSpace.validators';
import { ProfileGeneralInfoUpdateRequest } from './models/account-patch-body';


@Component({
  selector: 'app-account',
  imports: [MatGridListModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnDestroy {

  generalInfo! : string[];
  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder)


  userProfileGeneralInfo = this.formBuilder.group({
    firstname : ['', [Validators.maxLength(50),CustomValidators.WhiteSpaceValidator]],
    lastname : ['', [Validators.maxLength(80),CustomValidators.WhiteSpaceValidator]],
    username : ['', [Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]]
  })
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
  patchFormValues() {
    if(this.generalInfo) {
      const g = this.userProfileGeneralInfo.patchValue({
        
      })
    }
  }
  getFormRawValues() : ProfileGeneralInfoUpdateRequest {
    const formControlValues : ProfileGeneralInfoUpdateRequest = {
      firstname: this.userProfileGeneralInfo.controls.firstname.value ?? '', // si value  null ou undefined alors return droite
      lastname: this.userProfileGeneralInfo.controls.lastname.value ?? '',
      username: this.userProfileGeneralInfo.controls.username.value ?? ''
    }
    return formControlValues;
  }

}

