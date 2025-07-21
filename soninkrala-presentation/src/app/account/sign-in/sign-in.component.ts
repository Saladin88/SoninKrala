import { Component, OnDestroy, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
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
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ImmediateErrorStateMatcher } from '../../Validators/errorStateMatcher';
import configVariable from '../../../config/account-config.json'
import { ToasterService } from '../../toaster-service/toaster.service';
@Component({
  selector: 'app-sign-in',
  imports: [CommonModule,MatFormFieldModule, MatInputModule,MatIconModule, MatButtonModule, ReactiveFormsModule,MatDialogModule, MatSnackBarModule],
  templateUrl: './sign-in.component.html',
  styleUrl: './sign-in.component.css'
})
export class SignInComponent implements OnDestroy {

  username: string = configVariable.config.label.username;
  password: string = configVariable.config.label.password;
  title: string =  configVariable.config.title.login;
  hintUsername: string = configVariable.config.hint.username;
  spaceErrorText: string = configVariable.config.error.space;
  requiredErrorText: string = configVariable.config.error.required;
  hintPassword: string = configVariable.config.hint.password;
  loginBtnText: string = configVariable.config.action.login;
  registerBtnText: string = configVariable.config.action.register;
  credentialsErrorText: string = configVariable.config.error.badCredentials;
  minLengthErrorText: string = configVariable.config.error.minLengthPassword;

  invalidCredentials: boolean = false;
  errorMatcher = new ImmediateErrorStateMatcher();
  hidePassword = signal<Boolean>(true);
  private readonly subscriptions: Subscription[] = [];
  private readonly formBuilder = inject(FormBuilder)
  private readonly dialog = inject(MatDialog);
  private readonly toasterService = inject(ToasterService)

  loginFormGroup = this.formBuilder.group({
    username : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
    password : ['', [Validators.required, Validators.minLength(8), Validators.maxLength(20)]],
  })

  constructor(readonly accountService : AccountService, readonly authService : AuthService){}

  toggleVisibilityPassword(event : MouseEvent) {
    if(event.isTrusted) {
      event.preventDefault();
      this.hidePassword.set(!this.hidePassword());
    }
  }

  
  get usernameControl() : FormControl {
    return this.loginFormGroup.get('username') as FormControl
  }
  get passwordControl() : FormControl {
    return this.loginFormGroup.get('password') as FormControl
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
          this.sucessToaster()
        },
        error : (err) => {
          if(err.status === 401) {
            this.invalidCredentials = true;
            // this.loginFormGroup.markAllAsTouched()
            this.setupFormListener()
            this.errorToaster()
            console.error(err)}
          }

      });
      this.subscriptions.push(formSub)
    }

  }

  openDialog(event : MouseEvent) {
    if(event.isTrusted) {
      this.dialog.open(AccountCreationViewComponent, {disableClose: true});
    }
  }

  sucessToaster() {
    this.toasterService.message = configVariable.config.login.sucessToasterMessage;
    this.toasterService.duration= 3;
    this.toasterService.successToaster()
  }
  errorToaster() {
    this.toasterService.message = configVariable.config.login.errorToasterMessage;
    this.toasterService.duration= 8;
    this.toasterService.errorToaster()
  } 
  closeErrorToasterIfDirty() {
    if(this.usernameControl.dirty || this.passwordControl.dirty) {
      this.toasterService.closeErrorToaster();
    }
  }
  setupFormListener() {
    const usernameSub = this.usernameControl.valueChanges.subscribe(()=> {
      this.closeErrorToasterIfDirty();
      this.invalidCredentials = false;
      this.subscriptions.push(usernameSub)
    });
    const passwordSub = this.passwordControl.valueChanges.subscribe(()=> {
      this.closeErrorToasterIfDirty();
      this.invalidCredentials = false;
      this.subscriptions.push(passwordSub)
    })
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
