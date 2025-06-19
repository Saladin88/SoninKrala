import { Component, OnDestroy, ViewEncapsulation, inject, signal } from '@angular/core';
import { AccountService } from '../service/account.service';
import { Subscription} from 'rxjs';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AccountCreationBody } from '../models/account-creation-body';
import { CustomValidators } from '../../Validators/whiteSpace.validators';
import { AccountResponseBody } from '../models/account-response-body';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DialogRef } from '@angular/cdk/dialog';


@Component({
  selector: 'app-account-creation-view',
  imports: [MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatIconModule, MatButtonModule, MatDialogModule, CommonModule],
  templateUrl: './account-creation-view.component.html',
  styleUrl: './account-creation-view.component.css',
  encapsulation: ViewEncapsulation.None

})
export class AccountCreationViewComponent implements OnDestroy {

  hidePassword = signal<Boolean>(true);
  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder);
  private readonly dialog = inject(DialogRef)
  isUserAlreadyExists : boolean = false;
  errorMessageMail! : string;
  errorMessageUsername! : string;


  constructor(readonly accountService : AccountService, readonly router : Router){}

  accountCreationFormGroup = this.formBuilder.group({
    firstname : ['', [Validators.required, Validators.maxLength(50),CustomValidators.WhiteSpaceValidator]],
    lastname : ['', [Validators.required, Validators.maxLength(80),CustomValidators.WhiteSpaceValidator]],
    username : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
    email : ['', [Validators.required, Validators.maxLength(100), Validators.email,CustomValidators.WhiteSpaceValidator]],
    password : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
  })


  toggleVisibilityPassword(event : MouseEvent) {
    if(event.isTrusted) {
      event.preventDefault();
      this.hidePassword.set(!this.hidePassword());
    }
  }

  getFormRawValues() : AccountCreationBody {
    const formControlValues : AccountCreationBody = {
      firstname: this.accountCreationFormGroup.controls.firstname.value ?? '', // si value  null ou undefined alors return droite
      lastname: this.accountCreationFormGroup.controls.lastname.value ?? '',
      username: this.accountCreationFormGroup.controls.username.value ?? '',
      email: this.accountCreationFormGroup.controls.email.value ?? '',
      password: this.accountCreationFormGroup.controls.password.value ?? ''
    }
    return formControlValues;
  }
  closeDialog() {
    this.dialog.close();
  }

  onSubmit() {
    if(this.accountCreationFormGroup.valid) {
      const formSub = this.accountService.createAccount(this.getFormRawValues()).subscribe({
        next : (createdAccount : AccountResponseBody) => {
          console.log(createdAccount);
          window.alert("Veuillez vérifier votre boite mail, un lien de confirmation vous a été envoyé")
          this.resetForm();
          this.closeDialog();
          this.router.navigate(['/sign-in'])
        },
        error : (err) => {
          console.error(err)
          // if(err.error.fieldErrors) {
          //   this.isUserAlreadyExists = true;
          //   this.errorMessageMail = err.error.fieldErrors.email?.[0] ?? '';
          //   this.errorMessageUsername= err.error.fieldErrors.username?.[0] ?? '';
            // console.log(err.error.fieldErrors)
            window.alert("Le nom d'utilisateur et/ou l'adresse mail existent déjà")
          // }
        }
      })
      this.subscriptions.push(formSub);
    }
  }

  resetForm() {
    this.accountCreationFormGroup.reset({
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      password: ''
    });
  }

  ngOnDestroy(): void {
   this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}

