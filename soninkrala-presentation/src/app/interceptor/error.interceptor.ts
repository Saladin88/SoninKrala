import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error : HttpErrorResponse) => {
      console.error(error)
      switch(error.status) {
        // case 401 : 
        //  window.alert('Token expired, vous allez être déconnecter')
        //   localStorage.clear();
        //   router.navigate(['/home']);
        //   break;

        case 403 :
          router.navigate(['/unauthorized']);
          break;
        
        case 500 :
          router.navigate(['/error']);
          break;
        
        case 404 :
          router.navigate(['/**']);
          break;
        
      default:
        console.error('error = ', error)
        break;
      }
      return throwError(() => error) //propagation de l'erreur 
    })
  )
};
