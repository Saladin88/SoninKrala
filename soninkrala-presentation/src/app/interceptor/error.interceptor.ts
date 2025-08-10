import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { ToasterService } from '../toaster-service/toaster.service';
import commonConfigVariable from '../../config/common-config.json'


export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const toasterService = inject(ToasterService);
  const serverError = () => {
    toasterService.duration= 8;
    toasterService.errorToaster();
  }
  return next(req).pipe(
    catchError((error : HttpErrorResponse) => {
      console.error(error)
      switch(error.status) {
        case 400:
          return throwError(() => error);
        case 401 :
          if (req.url.includes('/register') || req.url.includes('/sign-in')) {
            break;
          }
        toasterService.message = 'Token expired, vous allez être déconnecter';
         serverError()
          localStorage.clear();
          router.navigate(['/home']);
          break;

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
        toasterService.message = commonConfigVariable.config.error.internalError;
        serverError();
        break;
      }
      return throwError(() => error) //propagation de l'erreur
    })
  )
};
