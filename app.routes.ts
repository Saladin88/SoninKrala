import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo:'home',
    pathMatch:'full'
  },
  {
    path: 'home',
    loadComponent: () => import('./home/home.component').then(m=> m.HomeComponent),
  },
  {
  path: 'quiz',
  canActivate: [authGuard,roleGuard],
  data: {roles: ['MEMBER']},
  loadComponent: () => import('./quiz/quiz.component').then(m=> m.QuizComponent),
},
{
  path: 'quiz/:id',
  canActivate: [authGuard,roleGuard],
    data: {roles: ['MEMBER']},
  loadComponent: () => import('./quiz/quiz.component').then(m=> m.QuizComponent),
},
{
  path: 'alphabet',
  canActivate: [authGuard,roleGuard],
  data: {roles: ['MEMBER']},
  loadComponent: () => import('./alphabet-audio-view/alphabet-audio-view.component').then(m=> m.AlphabetAudioViewComponent),

},
{
  path: 'about-us',
  loadComponent: () => import('./more-about-us/more-about-us.component').then(m => m.MoreAboutUsComponent)
},
{
  path: 'contact',
  loadComponent: () => import('./contact/contact.component').then(m => m.ContactComponent)
},
{
  path: 'account-validation',
  loadComponent: () => import('./account/account-validation/account-validation.component').then(m => m.AccountValidationComponent)

},
{
  path: 'sign-in',
  loadComponent: () => import('./account/sign-in/sign-in.component').then(m => m.SignInComponent)

},
{
  path: 'unauthorize',
  loadComponent: () => import('./unauthorize/unauthorize.component').then(m => m.UnauthorizeComponent)
},
{
  path: '**',
  loadComponent: () => import('./page-not-found-404/page-not-found-404.component').then(m => m.PageNotFound404Component)
}
];
