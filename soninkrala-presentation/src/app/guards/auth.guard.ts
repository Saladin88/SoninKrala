import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';




export const authGuard: CanActivateFn = () => {
  const router = inject(Router);
  const isLoggedIn = Boolean(localStorage.getItem('jwtToken'));
  if(!isLoggedIn) {
    router.navigate(['/home']);
    return false
  }
  return true;
};

export const roleGuard : CanActivateFn = (route, state) => {
  const router = inject(Router);
  const role = localStorage.getItem('role')
  const rolesAllowed : string[]=  route.data?.['roles'];


    if (!rolesAllowed || !Array.isArray(rolesAllowed)) {
        console.warn('Aucun rôle défini dans la route');
        router.navigate(['/unauthorize']);
        return false;
      }

      if (!rolesAllowed.includes(role || '')) {
        router.navigate(['/unauthorize']);
        return false;
      }

  return true

}
