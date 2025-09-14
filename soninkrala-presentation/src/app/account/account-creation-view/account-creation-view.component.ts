import { Component, OnDestroy, inject, signal } from '@angular/core';
import { AccountService } from '../service/account.service';
import { Subscription} from 'rxjs';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
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
import { ApiFieldError, ImmediateErrorStateMatcher } from '../../Validators/errorStateMatcher';
import accountConfigVariable from '../../../config/account-config.json';
import commonConfigVariable from '../../../config/common-config.json';
import termsConfigVariable from '../../../config/terms-condition-config.json';
import { ToasterService } from '../../toaster-service/toaster.service';
import {MatCheckboxModule} from '@angular/material/checkbox';

@Component({
  selector: 'app-account-creation-view',
  imports: [MatInputModule, MatFormFieldModule, ReactiveFormsModule, MatIconModule, MatButtonModule, MatDialogModule, CommonModule,MatCheckboxModule],
  templateUrl: './account-creation-view.component.html',
  styleUrl: './account-creation-view.component.css',

})
export class AccountCreationViewComponent implements OnDestroy {
  errorMatcher = new ImmediateErrorStateMatcher();

  title: string =  accountConfigVariable.config.title.register;
  username: string = accountConfigVariable.config.label.username;
  password: string = accountConfigVariable.config.label.password;
  firstname: string = accountConfigVariable.config.label.firstname;
  lastname: string = accountConfigVariable.config.label.lastname;
  email: string = accountConfigVariable.config.label.email;
  hintUsername: string = accountConfigVariable.config.hint.username;
  hintPassword: string = accountConfigVariable.config.hint.password;
  hintFirstname: string = accountConfigVariable.config.hint.firstname;
  hintLastname: string = accountConfigVariable.config.hint.lastname;
  spaceErrorText: string = accountConfigVariable.config.error.space;
  requiredErrorText: string = accountConfigVariable.config.error.required;
  minLengthErrorText: string = accountConfigVariable.config.error.minLengthPassword;
  emailErrorText: string = accountConfigVariable.config.error.email;
  uniqueUsernameErrorText: string = accountConfigVariable.config.error.uniqueUsername;
  uniqueEmailErrorText: string = accountConfigVariable.config.error.uniqueEmail;
  registerBtnText: string = accountConfigVariable.config.action.createAccount;
  cancelBtnText: string = commonConfigVariable.config.action.cancelBtn;
  termsAgreement : string = termsConfigVariable.config.label.acceptTermsAndRgpd;


  invalidCredentials: boolean = false;
  hidePassword = signal<Boolean>(true);
  private readonly subscriptions: Subscription[] = [];
  isUserAlreadyExists : boolean = false;

  private readonly toasterService = inject(ToasterService);
  private readonly formBuilder = inject(FormBuilder);

  private readonly dialog = inject(DialogRef)

  constructor(readonly accountService : AccountService, readonly router : Router){}

  accountCreationFormGroup = this.formBuilder.group({
    firstname : ['', [Validators.required, Validators.maxLength(50),CustomValidators.WhiteSpaceValidator]],
    lastname : ['', [Validators.required, Validators.maxLength(80),CustomValidators.WhiteSpaceValidator]],
    username : ['', [Validators.required, Validators.maxLength(20),CustomValidators.WhiteSpaceValidator]],
    email : ['', [Validators.required, Validators.maxLength(100), Validators.email,CustomValidators.WhiteSpaceValidator]],
    password : ['', [Validators.required,Validators.minLength(8), Validators.maxLength(20)]],
    isTermsAccepted : [false, [Validators.requiredTrue]]
  })


  get firstnameControl() : FormControl {
    return this.accountCreationFormGroup.get('firstname') as FormControl
  }
  get lastnameControl() : FormControl {
    return this.accountCreationFormGroup.get('lastname') as FormControl
  }
  get usernameControl() : FormControl {
    return this.accountCreationFormGroup.get('username') as FormControl
  }
  get emailControl() : FormControl {
    return this.accountCreationFormGroup.get('email') as FormControl
  }
  get passwordControl() : FormControl {
    return this.accountCreationFormGroup.get('password') as FormControl
  }
  get isTermsAccepted() : FormControl {
    return this.accountCreationFormGroup.get('isTermsAccepted') as FormControl
  }


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
      password: this.accountCreationFormGroup.controls.password.value ?? '',
      isRgpdAgreed: this.accountCreationFormGroup.controls.isTermsAccepted.value ?? false

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
          this.resetForm();
          this.closeDialog();
          this.sucessToaster();
          this.router.navigate(['/sign-in'])
        },
        error : (err) => {
          const fieldErrors = err?.error?.fieldErrors;
          if (fieldErrors) {
            Object.entries(fieldErrors).forEach(([fieldName, errors]) => {
              const control = this.accountCreationFormGroup.get(fieldName);
              if (control && Array.isArray(errors)) {
                errors.forEach((errorCode) => {
                  ApiFieldError.apply(control, errorCode);
                });
              }
            });
          }

        }
      })
      this.subscriptions.push(formSub);
    }
  }
  sucessToaster() {
    this.toasterService.message = accountConfigVariable.config.register.sucessToasterMessage;
    this.toasterService.duration= 3;
    this.toasterService.successToaster()
  }
  errorToaster() {
    this.toasterService.message = accountConfigVariable.config.login.errorToasterMessage;
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
      ApiFieldError.remove(this.usernameControl, 'uniqueUsername');
      this.subscriptions.push(usernameSub)
    });
    const emailSub = this.emailControl.valueChanges.subscribe(()=> {
      this.closeErrorToasterIfDirty();
      this.invalidCredentials = false;
      ApiFieldError.remove(this.emailControl, 'UniqueEmail');
      this.subscriptions.push(emailSub)
    })
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

