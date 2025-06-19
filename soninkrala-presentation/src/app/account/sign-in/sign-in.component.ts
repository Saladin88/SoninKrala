import { Component, OnDestroy, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AccountService } from '../service/account.service';
import { CustomValidators } from '../../Validators/whiteSpace.validators';
import { LoginRequestBody } from '../models/account-creation-body';
import { LoginResponseBody } from '../models/account-response-body';
import { AuthService } from '../auth-service/auth.service';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import { AccountCreationViewComponent } from '../account-creation-view/account-creation-view.component';

@Component({
  selector: 'app-sign-in',
  imports: [CommonModule,MatFormFieldModule, MatInputModule,MatIconModule, MatButtonModule, ReactiveFormsModule,MatDialogModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent implements OnDestroy {
  hidePassword = signal<Boolean>(true);

  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder)
  private readonly dialog = inject(MatDialog);

  loginFormGroup = this.formBuilder.group({
    username : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
    password : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
  })

  constructor(readonly accountService : AccountService, readonly authService : AuthService){}

  toggleVisibilityPassword(event : MouseEvent) {
    if(event.isTrusted) {
      event.preventDefault();
      this.hidePassword.set(!this.hidePassword());
    }
  }

  getRawValues() : LoginRequestBody {
    const formControlValues : LoginRequestBody = {
      username: this.loginFormGroup.controls.username.value ?? '',
      password: this.loginFormGroup.controls.password.value ?? '',
    }
    return formControlValues;
  }

  onSubmit() {
    if(this.loginFormGroup.valid) {
      const formSub = this.accountService.login(this.getRawValues()).subscribe({
        next : (response : LoginResponseBody) => {
          console.log(response)
          this.resetForm();
          this.authService.storeResponseLogin(response.token, response.role)
          window.alert("sucessfully connected")
        },
        error : (err) => {
          window.alert("Bad Credential")
          console.error(err)}

      });
      this.subscriptions.push(formSub)
    }

  }

  openDialog(event : MouseEvent) {
    if(event.isTrusted) {
      this.dialog.open(AccountCreationViewComponent, {disableClose: true});
    }
  }

  resetForm() {
    this.loginFormGroup.reset({
      username : "",
      password : ""
    })
  }
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
