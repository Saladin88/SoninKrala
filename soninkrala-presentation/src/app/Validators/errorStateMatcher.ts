import { ErrorStateMatcher } from '@angular/material/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm } from '@angular/forms';

export class ImmediateErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    return !!(control && control.invalid && (control.dirty || control.touched || control.value?.length > 0));
  }
}

export class ApiFieldError {
  static apply(control: AbstractControl, errorCode: string) {
    control.setErrors({
      ...(control.errors || {}),
      [errorCode]: true,
    });
  }

  static remove(control: FormControl, errorCode: string) {
    const { [errorCode]: removed, ...otherErrors } = control.errors || {};
    control.setErrors(Object.keys(otherErrors).length ? otherErrors : null);
  }
}
