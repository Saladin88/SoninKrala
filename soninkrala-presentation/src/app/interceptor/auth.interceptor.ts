import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../account/auth-service/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if(req.url.includes('/sign-in')) {
    return next(req);
  }
  const auth = inject(AuthService) //pour injecter le service AuthService dans l'intercepteur et obtenir le token d'authentification.
  const token = auth.token
  if(!token) {
    return next(req);
  }
  const newReq = req.clone({ // cloner pour eviter de modfier la requete originale
    headers: req.headers.set('Authorization', `Bearer ${token}`) // Protocole HTTP oblige d'avoir le type de token suivi du token
  })
  return next(newReq)
};
