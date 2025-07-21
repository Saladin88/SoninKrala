import { AbstractControl, ValidationErrors } from "@angular/forms";

export class CustomValidators {

  static WhiteSpaceValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    if (value != null) {
      const trimmed = value.trim();
      if (trimmed.length === 0 && value.length > 0) { // si que des espaces, et pas vide
        return { noSpaceAllowed: true };
      }
    }
    return null;
  }
  

  }

