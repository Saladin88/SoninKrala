import { Component, OnDestroy, OnInit, Signal, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountService } from './service/account.service';
import {MatGridListModule} from '@angular/material/grid-list';
import { CustomValidators } from '../Validators/whiteSpace.validators';
import { ProfileGeneralInfoUpdateRequest, UpdateProfileGeneralInfoResponse } from './models/account-patch-body';
import { AccountGeneralInfo } from './models/account-general-info';
import { Tile } from '../utils/tile-utils';
import {MatCardModule} from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import configVariable from '../../config/account-config.json';
import commonConfigVariable from '../../config/common-config.json';
import { ImmediateErrorStateMatcher } from '../Validators/errorStateMatcher';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from "@angular/material/dialog";
import { ToasterService } from '../toaster-service/toaster.service';


@Component({
  selector: 'app-account',
  imports: [MatGridListModule, MatCardModule, MatFormFieldModule, ReactiveFormsModule, MatInputModule, MatButtonModule, MatDialogModule],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit, OnDestroy {
  errorMatcher = new ImmediateErrorStateMatcher();
  
  generalInfo!: Signal<AccountGeneralInfo | null>;
  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder);
  private readonly toasterService = inject(ToasterService);
  isUpdateButtoClicked : boolean = true;
  title: string =  configVariable.config.title.register;
  username: string = configVariable.config.label.username;
  password: string = configVariable.config.label.password;
  firstname: string = configVariable.config.label.firstname;
  lastname: string = configVariable.config.label.lastname;
  email: string = configVariable.config.label.email;
  spaceErrorText: string = configVariable.config.error.space;
  minLengthErrorText: string = configVariable.config.error.minLengthPassword;
  emailErrorText: string = configVariable.config.error.email;
  uniqueUsernameErrorText: string = configVariable.config.error.uniqueUsername;
  uniqueEmailErrorText: string = configVariable.config.error.uniqueEmail;
  modificationBtnText: string = configVariable.config.action.modify;
  cancelBtnText: string = commonConfigVariable.config.action.cancelBtn;

  userProfileGeneralInfo = this.formBuilder.group({
    firstname : ['', [Validators.maxLength(50),CustomValidators.WhiteSpaceValidator]],
    lastname : ['', [Validators.maxLength(80),CustomValidators.WhiteSpaceValidator]],
    username : ['', [Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]]
  })
  constructor(readonly accountService : AccountService){}
  ngOnInit(): void {
    this.generalInfo = this.accountService.profilInfoSignal; 
    this.retrieveGeneralInfo();
  
  }
  retrieveGeneralInfo() {
    this.accountService.retrieveGeneralInfoProfile().subscribe({
      next : (response : AccountGeneralInfo) => {
        this.accountService.profilInfoSignal.set(response);
        this.patchFormValues(response);
        console.log(response)
      },
      error : err => console.error(err)
    })
  }

  // tiles: Tile[] = [
  //   new Tile(title: 'Quiz', cols: 3, rows: 1, color: 'lightblue'),
  //   new Tile((title: 'Account info', cols: 1, rows: 3, color: 'lightgreen'),
  //   new Tile(title: 'Three', cols: 3, rows: 0.2, color: 'blue'),
  //   new Tile(title: 'Pronunciation', cols: 3, rows: 1, color: '#DDBDF1'),
  // ];

  tiles: Tile[] = [
    new Tile('quiz', 3, 2, '#7CA9E3', 'Derniers quiz'),
    new Tile('account', 2, 4, '', 'Informations du compte'),
    new Tile('pronunciation', 3, 2, '#F77733', 'Prononciations'),
  ];

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
  patchFormValues(response : AccountGeneralInfo ) {
    const info = this.generalInfo();  
    if (!info) return;
    this.userProfileGeneralInfo.patchValue({
      firstname: response.firstname,
      lastname:  response.lastname,
      username:  response.username,
    });

  }

  onSubmit() {
    if(this.userProfileGeneralInfo.valid) {
      const formsub = this.accountService.updateAccountInfo(this.getFormRawValues()).subscribe({
        next : (response : UpdateProfileGeneralInfoResponse) => {
          console.log(response);
          this.sucessToaster();
        },
        error : (err) => console.error(err)
        
      })
      this.subscriptions.push(formsub);
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
  sucessToaster() {
    this.toasterService.message = configVariable.config.modify.sucessToasterMessage;
    this.toasterService.duration= 3;
    this.toasterService.successToaster()
  }

}

