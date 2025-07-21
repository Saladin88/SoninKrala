import { Injectable, Input, inject } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ToasterService {
private _snackBar = inject(MatSnackBar);
@Input() message : string = "";
@Input() duration! : number;

successToaster() {
  this._snackBar.open(this.message,'close', {
    duration : this.duration * 1000,
    panelClass:['success-snackbar'],
    direction : 'ltr',
    horizontalPosition: 'right',
    verticalPosition: 'top'
  } as MatSnackBarConfig)
}

errorToaster() {
  this._snackBar.open(this.message,'close', {
    duration : this.duration * 1000,
    panelClass:['error-snackbar'],
    direction : 'ltr',
    horizontalPosition: 'right',
    verticalPosition: 'top'
  } as MatSnackBarConfig)

}

closeErrorToaster() {
  this._snackBar.dismiss();
}

}
