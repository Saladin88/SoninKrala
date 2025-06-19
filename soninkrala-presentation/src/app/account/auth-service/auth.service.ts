import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

router = inject(Router);

logout(event : MouseEvent) : void {
  if(event.isTrusted) {
    localStorage.clear();
    this.router.navigate(['/home'])
  }
}

storeResponseLogin( token : string, role : string ) : void {
  localStorage.setItem("jwtToken", token)
  localStorage.setItem("role", role)
}
}
