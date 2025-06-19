import { AbstractControl } from "@angular/forms";

export class CustomValidators {

  static WhiteSpaceValidator(control : AbstractControl) {
    if(control.value != null && control.value.trim().length === 0) {
      return {noSpaceAllowed : true}
  }
  return null;
}

  }

